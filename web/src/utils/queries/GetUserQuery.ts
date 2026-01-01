import FetchClient, { IResponseError } from "@/utils/FetchClient";
import { json, query } from "@solidjs/router";
import { useToast } from "solid-notifications";

const key = "GetUserQuery";
const GetUserQuery = (revalidate?: string[]) => {
  const client = FetchClient({ hasAuth: true });
  const { notify } = useToast();

  const handler = async (userId: string) => {
    if (!userId) {
      return json({ ok: false, data: {} }, { revalidate });
    }

    try {
      const res = await client.request(`/api/v1/user/${userId}`, {
        method: "GET",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      });

      const resBody = (await res.json()) as IGetUserRes;
      return json({ ok: true, data: resBody }, { revalidate });
    } catch (err) {
      const error = JSON.parse((err as Error).message) as IResponseError;
      notify(error.message, { type: "error" });
      return json({ ok: false, data: error.message }, { revalidate });
    }
  };

  return {
    handler,
    query: query(handler, key),
    key,
  };
};

export default GetUserQuery;

export interface IGetUserRes {
  result: {
    id: string;
    isDeleted: boolean;
    version: number;
    createBy: string;
    createDate: string;
    updateBy: string;
    updateDate: string;
    username: string;
    name: string;
    roleList: string[];
  };
}
