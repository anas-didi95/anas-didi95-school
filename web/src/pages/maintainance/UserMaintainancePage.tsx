import { usePageContext } from "@/contexts/PageContext";
import GetUserQuery, { IGetUserRes } from "@/utils/queries/GetUserQuery";
import { createAsync, useParams } from "@solidjs/router";
import { createEffect, onMount } from "solid-js";

export default function UserMaintainancePage() {
  const params = useParams();
  const pageContext = usePageContext();
  const getUserQuery = createAsync(() => GetUserQuery().query(params.id!));

  onMount(() => {
    pageContext.action.setEditMode(false);
  });

  createEffect(() => {
    if (!getUserQuery()?.ok) return;

    const user = (getUserQuery()?.data as IGetUserRes).result;
    pageContext.action.setBreadcrumbs(["User Maintainance", user.name]);
  });

  return <div>UserMaintainancePage : {params.id}</div>;
}
