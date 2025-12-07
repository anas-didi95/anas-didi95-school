const FetchClient = (props: IFetchClient = {}) => {
  const {
    requestMiddlewares = [],
    responseMiddlewares = [],
    timeoutMs = 5000,
    retryCount = 2,
    retryDelayMs = 500,
  } = props;

  const applyRequestMiddleware = async (
    config: RequestInit & { url?: string },
  ): Promise<RequestInit & { url?: string }> => {
    for (const mw of requestMiddlewares) {
      config = (await mw(config)) || config;
    }
    return config;
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
      try {
        const { url: finalUrl, ...finalConfig } = mergedConfig;

        const response = await fetchWithTimeout(finalUrl ?? url, finalConfig);
        const processedResponse = await applyResponseMiddleware(response);

        if (!processedResponse.ok) {
          throw new Error(`HTTP ${processedResponse.status}`);
        }

        return processedResponse;
      } catch (err: unknown) {
        const error = err instanceof Error ? err : new Error(String(err));
        lastError = error;

        if (error.name === "AbortError" || attempt === retryCount) {
          throw error;
        }

        const delay = retryDelayMs * Math.pow(2, attempt);
        await new Promise((res) => setTimeout(res, delay));
        attempt++;
      }
    }

    throw lastError;
  };

  return { request };
};

export default FetchClient;

interface IFetchClient {
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
