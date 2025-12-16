import FetchClient, { IResponseError } from "@/utils/FetchClient";
import SessionUtil from "@/utils/SessionUtil";
import { action, json } from "@solidjs/router";
import { useToast } from "solid-notifications";

const key = "SignOutAction";
const SignOutAction = (revalidate?: string[]) => {
  const client = FetchClient({ retryCount: 0 });
  const sessionUtil = SessionUtil();
  const { notify } = useToast();

  return {
    action: action(async () => {
      try {
        await client.request("/api/v1/auth/sign-out", {
          method: "GET",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
            Authorization: `Bearer ${sessionUtil.getToken()?.token.access_token}`,
          },
        });

        return json({ ok: true }, { revalidate });
      } catch (err) {
        const error = JSON.parse((err as Error).message) as IResponseError;
        notify(error.message, { type: "error" });
        return json({ ok: false, data: error.message }, { revalidate });
      }
    }, key),
    key,
  };
};

export default SignOutAction;
