import FetchClient, { IResponseError } from "@/utils/FetchClient";
import { action, json } from "@solidjs/router";
import { useToast } from "solid-notifications";

const key = "SignInAction";
const SignInAction = (revalidate?: string[]) => {
  const client = FetchClient({ retryCount: 0 });
  const { notify } = useToast();

  return {
    action: action(async (req: ISignInReq) => {
      try {
        const res = await client.request("/api/v1/auth/sign-in", {
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
        const error = JSON.parse((err as Error).message) as IResponseError;
        notify(error.message, { type: "error" });
        return json({ ok: false, data: error.message }, { revalidate });
      }
    }, key),
    key,
  };
};

export default SignInAction;

export interface ISignInReq {
  username: string;
  password: string;
}

export interface ISignInRes {
  token: {
    access_token: string;
    refresh_token: string;
    token_type: string;
    expires_in: number;
  };
}
