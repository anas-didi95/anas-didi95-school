import SessionUtil from "@/utils/SessionUtil";
import RefreshTokenAction from "./actions/RefreshTokenAction";
import { ISignInRes } from "./actions/SignInAction";

const FetchClient = (props: IFetchClient = {}) => {
  const {
    requestMiddlewares = [],
    responseMiddlewares = [],
    timeoutMs = 5000,
    retryCount = 2,
    retryDelayMs = 500,
    hasAuth = false,
  } = props;
  const session = SessionUtil();

  const applyRequestMiddleware = async (
    config: RequestInit & { url?: string },
  ): Promise<RequestInit & { url?: string }> => {
    for (const mw of requestMiddlewares) {
      config = (await mw(config)) || config;
    }

    if (hasAuth) {
      const signIn = session.getSignIn();
      return {
        ...config,
        headers: {
          ...(config.headers ?? {}),
          Authorization: signIn ? `Bearer ${signIn.token?.access_token}` : "",
        },
      };
    } else {
      return config;
    }
  };

  const applyResponseMiddleware = async (
    response: Response,
  ): Promise<Response> => {
    for (const mw of responseMiddlewares) {
      response = (await mw(response)) || response;
    }
    return response;
  };

  const fetchWithTimeout = async (
    url: string,
    config: RequestInit,
  ): Promise<Response> => {
    const controller = new AbortController();
    const timer = setTimeout(() => controller.abort(), timeoutMs);

    try {
      return await fetch(url, { ...config, signal: controller.signal });
    } finally {
      clearTimeout(timer);
    }
  };

  const request = async (
    url: string,
    config: RequestInit = {},
  ): Promise<Response> => {
    let mergedConfig = await applyRequestMiddleware({ ...config, url });

    let attempt = 0;
    let lastError: unknown;
    let hasRefreshToken = false;

    while (attempt <= retryCount) {
      const { url: finalUrl, ...finalConfig } = mergedConfig;

      const response = await fetchWithTimeout(finalUrl ?? url, finalConfig);
      const processedResponse = await applyResponseMiddleware(response);

      if (processedResponse.ok) {
        return processedResponse;
      }

      let error: IResponseError = { canRetry: false, message: "" };
      if (processedResponse.status === 401) {
        error.canRetry = !hasRefreshToken;

        if (error.canRetry) {
          console.log("do refresh 1");
          hasRefreshToken = true;

          const signIn = session.getSignIn();
          const jwt = signIn?.token.access_token ?? "";
          const refreshToken = signIn?.token.refresh_token ?? "";
          console.log("jwt", jwt);
          console.log("refreshToken", refreshToken);

          const refreshTokenHandler = RefreshTokenAction().handler;

          console.log("BEFORE");
          const refreshTokenResponse = await refreshTokenHandler({
            jwt,
            refreshToken,
          });
          error.canRetry = refreshTokenResponse.ok;

          if (error.canRetry) {
            console.log("refresh success");
            const newSignIn = (await refreshTokenResponse.json()) as {
              ok: boolean;
              data: ISignInRes;
            };
            session.putSignIn(newSignIn.data);

            mergedConfig = {
              ...mergedConfig,
              headers: {
                ...mergedConfig.headers,
                Authorization: `Bearer ${newSignIn.data.token.access_token}`,
              },
            };
            attempt--;
          } else {
            console.log("refresh failed");
            session.clear();
          }
        }

        console.log("Final canRetry", error.canRetry);
        error = {
          canRetry: error.canRetry,
          message: "Unauthenticated. Please re-login.",
        };
      } else {
        const message = await processedResponse.text();
        error = {
          canRetry: true,
          message,
        };
      }

      lastError = Error(JSON.stringify(error));
      if (error.canRetry) {
        const delay = retryDelayMs * Math.pow(2, attempt);
        await new Promise((res) => setTimeout(res, delay));
        attempt++;
      } else {
        throw lastError;
      }
    }
    throw lastError;
  };

  return { request };
};

export default FetchClient;

interface IFetchClient {
  hasAuth?: boolean;
  timeoutMs?: number;
  retryCount?: number;
  retryDelayMs?: number;
  requestMiddlewares?: RequestMiddleware[];
  responseMiddlewares?: ResponseMiddleware[];
}

type RequestMiddleware = (
  config: RequestInit & { url?: string },
) => Promise<RequestInit & { url?: string }> | (RequestInit & { url?: string });

type ResponseMiddleware = (response: Response) => Promise<Response> | Response;

export interface IResponseError {
  canRetry: boolean;
  message: string;
}
