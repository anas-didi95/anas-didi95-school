import FetchClient, { IResponseError } from "@/utils/FetchClient";
import { action, json } from "@solidjs/router";
import { useToast } from "solid-notifications";

const key = "CreateUserAction";
export default function CreateUserAction(revalidate?: string[]) {
  const client = FetchClient({ retryCount: 0, hasAuth: true });
  const { notify } = useToast();

  const handler = async (req: ICreateUserReq) => {
    try {
      const res = await client.request(`/api/v1/user`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ ...req }),
      });

      const resBody = (await res.json()) as ICreateUserRes;
      return json({ ok: true, data: resBody }, { revalidate });
    } catch (err) {
      const error = JSON.parse((err as Error).message) as IResponseError;
      notify(error.message, { type: "error" });
      return json({ ok: false, data: error.message }, { revalidate });
    }
  };

  return {
    handler,
    action: action(handler, key),
    key,
  };
}

export interface ICreateUserReq {
  username: string;
  name: string;
  password: string;
}

export interface ICreateUserRes {
  id: string;
}
