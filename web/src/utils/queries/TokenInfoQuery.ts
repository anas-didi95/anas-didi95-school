import SessionUtil from "@/utils//SessionUtil";
import FetchClient, { IResponseError } from "@/utils/FetchClient";
import { json, query } from "@solidjs/router";
import { useToast } from "solid-notifications";

const key = "TokenInfoQuery";
const TokenInfoQuery = (revalidate?: string[]) => {
  const client = FetchClient();
  const sessionUtil = SessionUtil();
  const { notify } = useToast();

  return {
    query: query(async () => {
      try {
        const res = await client.request("/api/v1/auth/token-info", {
          method: "GET",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
            Authorization: `Bearer ${sessionUtil.getToken()?.token.access_token}`,
          },
        });

        const resBody = (await res.json()) as ITokenInfoRes;
        return json({ ok: true, data: resBody }, { revalidate });
      } catch (err) {
        const error = JSON.parse((err as Error).message) as IResponseError;
        notify(error.message, { type: "error" });
        return json({ ok: false, data: error.message }, { revalidate });
      }
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
