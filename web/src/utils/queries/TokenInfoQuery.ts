import FetchClient from "@/utils/FetchClient";
import { json, query } from "@solidjs/router";

const key = "TokenInfoQuery";
const TokenInfoQuery = (revalidate?: string[]) => {
  const client = FetchClient();
  return {
    query: query(async () => {
      const res = await client.request("/api/v1/auth/token-info", {
        method: "GET",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      });

      if (!res.ok) {
        const resBody = await res.text();
        return json({ ok: false, data: resBody }, { revalidate });
      }

      const resBody = (await res.json()) as ITokenInfoRes;
      return json({ ok: true, data: resBody }, { revalidate });
    }, key),
    key,
  };
};

export default TokenInfoQuery;

export interface ITokenInfoRes {
  result: {
    __userId: string;
    roles: string[];
    active: boolean;
    username: string;
    exp: number;
    iat: number;
    nbf: number;
    sub: string;
    iss: string;
  };
}
