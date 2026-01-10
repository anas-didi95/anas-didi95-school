import FetchClient, { IResponseError } from "@/utils/FetchClient";
import { action, json } from "@solidjs/router";
import { ISignInRes } from "./SignInAction";

const key = "RefreshTokenAction";
const RefreshTokenAction = (revalidate?: string[]) => {
  const client = FetchClient({ retryCount: 0, hasAuth: false });
  console.log("INI");

  console.log("INI 2");
  const handler = async (req: IRefreshTokenReq) => {
    try {
      const res = await client.request("/api/v1/auth/refresh-token", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ ...req }),
      });

      const resBody = (await res.json()) as ISignInRes;
      return json({ ok: true, data: resBody }, { revalidate });
    } catch (err) {
      console.log(err);
      const error = JSON.parse((err as Error).message) as IResponseError;
      return json({ ok: false, data: error.message }, { revalidate });
    }
  };

  return {
    handler,
    action: action(handler, key),
    key,
  };
};

export default RefreshTokenAction;

export interface IRefreshTokenReq {
  jwt: string;
  refreshToken: string;
}
