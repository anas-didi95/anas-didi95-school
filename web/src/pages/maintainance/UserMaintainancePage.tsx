import Button from "@/components/Button";
import Card from "@/components/Card";
import { usePageContext } from "@/contexts/PageContext";
import UserForm, { IUserModel } from "@/forms/UserForm";
import UpdateUserAction from "@/utils/actions/UpdateUserAction";
import GetUserQuery, { IGetUserRes } from "@/utils/queries/GetUserQuery";
import { createAsync, useAction, useParams } from "@solidjs/router";
import { createEffect, onMount, Show } from "solid-js";
import { useToast } from "solid-notifications";

export default function UserMaintainancePage() {
  const params = useParams();
  const pageContext = usePageContext();
  const getUserQuery = createAsync(() => GetUserQuery().query(params.id!));
  const updateUserAction = useAction(
    UpdateUserAction([GetUserQuery().key]).action,
  );
  const { notify } = useToast();

  const handleEdit = async (values: IUserModel) => {
    const res = await updateUserAction(params.id!, { ...values });
    if (res.ok) {
      pageContext.action.setEditMode(false);
      notify("Success", { type: "success" });
    }
  };

  onMount(() => {
    pageContext.action.setEditMode(false);
  });

  createEffect(() => {
    if (!getUserQuery()?.ok) return;

    const user = (getUserQuery()?.data as IGetUserRes).result;
    pageContext.action.setBreadcrumbs(["User Maintainance", user.name]);
  });

  return (
    <Card title={`${pageContext.store.isEditMode ? "Edit" : "View"} User`}>
      <UserForm
        isEditMode={pageContext.store.isEditMode}
        data={(getUserQuery()?.data as IGetUserRes)?.result}
        onSubmit={handleEdit}>
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
