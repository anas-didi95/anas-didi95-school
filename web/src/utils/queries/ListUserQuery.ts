import { IPagination } from "@/components/Table";
import { IUserModel } from "@/forms/UserForm";
import FetchClient, { IResponseError } from "@/utils/FetchClient";
import { json, query } from "@solidjs/router";
import { useToast } from "solid-notifications";

const key = "ListUserQuery";
const ListUserQuery = (revalidate?: string[]) => {
  const client = FetchClient({ hasAuth: true });
  const { notify } = useToast();

  const handler = async (pageNo: number, username: string, name: string) => {
    const param: IParam = { pageNo, totalRecordsPerPage: 2 };
    if (username) {
      param.username = username;
    }
    if (name) {
      param.name = name;
    }

    const paramStr = Object.keys(param)
      .map(
        (k) => `${k}=${encodeURIComponent(param[k as keyof IParam] as string)}`,
      )
      .join("&");

    try {
      const res = await client.request(`/api/v1/user?${paramStr}`, {
        method: "GET",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      });

      const resBody = (await res.json()) as IGetUserListRes;
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

export default ListUserQuery;

export interface IGetUserListRes {
  resultList: IUserModel[];
  pagination: IPagination;
}

interface IParam {
  pageNo: number;
  totalRecordsPerPage: number;
  username?: string;
  name?: string;
}
