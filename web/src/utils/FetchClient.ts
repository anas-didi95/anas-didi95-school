import SessionUtil from "@/utils/SessionUtil";

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
    const mergedConfig = await applyRequestMiddleware({ ...config, url });

    let attempt = 0;
    let lastError: unknown;

    while (attempt <= retryCount) {
      const { url: finalUrl, ...finalConfig } = mergedConfig;

      const response = await fetchWithTimeout(finalUrl ?? url, finalConfig);
      const processedResponse = await applyResponseMiddleware(response);

      if (processedResponse.ok) {
        return processedResponse;
      }

      let error: IResponseError = { canRetry: false, message: "" };
      if (processedResponse.status === 401) {
        error = {
          canRetry: false,
          message: "Unauthenticated. Please re-login.",
        };
        session.clear();
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
