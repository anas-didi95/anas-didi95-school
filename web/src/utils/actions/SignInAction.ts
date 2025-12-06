import { action, json } from "@solidjs/router";

const key = "SignInAction";
const SignInAction = (revalidate?: string[]) => {
  return {
    action: action(async (req: ISignInReq) => {
      const res = await fetch("/api/v1/auth/sign-in", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ ...req }),
      });

      if (!res.ok) {
        const resBody = await res.text();
        return json({ ok: false, data: resBody }, { revalidate });
      }

      const resBody = (await res.json()) as ISignInRes;
      return json({ ok: true, data: resBody }, { revalidate });
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
