import Button from "@/components/Button";
import Card from "@/components/Card";
import { usePageContext } from "@/contexts/PageContext";
import UserForm, { IUserModel } from "@/forms/UserForm";
import UpdateUserAction from "@/utils/actions/UpdateUserAction";
import GetUserQuery, { IGetUserRes } from "@/utils/queries/GetUserQuery";
import TokenInfoQuery, { ITokenInfoRes } from "@/utils/queries/TokenInfoQuery";
import { createAsync, useAction } from "@solidjs/router";
import { createEffect, createSignal, onMount, Show } from "solid-js";
import { useToast } from "solid-notifications";

export default function ProfilePage() {
  const [userId, setUserId] = createSignal("");
  const pageContext = usePageContext();
  const tokenInfoQuery = createAsync(() => TokenInfoQuery().query());
  const getUserQuery = createAsync(() => GetUserQuery().query(userId()));
  const updateUserAction = useAction(
    UpdateUserAction([GetUserQuery().key]).action,
  );
  const { notify } = useToast();

  const handleSubmit = async (values: IUserModel) => {
    const res = await updateUserAction(userId(), { ...values });
    if (res.ok) {
      pageContext.action.setEditMode(false);
      notify("Success", { type: "success" });
    }
  };

  onMount(() => {
    pageContext.action.setEditMode(false);
  });

  createEffect(() => {
    if (!tokenInfoQuery()?.ok) return;

    const result = (tokenInfoQuery()?.data as ITokenInfoRes).result;
    setUserId(result.__userId);
  });

  createEffect(() => {
    if (pageContext.store.isEditMode) return;
    if (!getUserQuery()?.ok) return;

    const values = getUserQuery()?.data as IGetUserRes;
    pageContext.action.setBreadcrumbs(["Profile", values.result.name]);
  });

  return (
    <Card title={`${pageContext.store.isEditMode ? "Edit" : "View"} Profile`}>
      <UserForm
        isEditMode={pageContext.store.isEditMode}
        data={(getUserQuery()?.data as IGetUserRes)?.result}
        onSubmit={handleSubmit}>
        <Show
          when={pageContext.store.isEditMode}
          fallback={
            <Button
              label="Edit"
              type="button"
              color="primary"
              onClick={() => pageContext.action.setEditMode(true)}
            />
          }>
          <Button
            label="Cancel"
            type="button"
            onClick={() => pageContext.action.setEditMode(false)}
          />
          <Button label="Update" type="submit" color="primary" />
        </Show>
      </UserForm>
    </Card>
  );
}
