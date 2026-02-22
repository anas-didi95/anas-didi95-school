import Button from "@/components/Button";
import Card from "@/components/Card";
import { usePageContext } from "@/contexts/PageContext";
import UserForm, { IUserModel } from "@/forms/UserForm";
import CreateUserAction, {
  ICreateUserRes,
} from "@/utils/actions/CreateUserAction";
import { useAction, useNavigate } from "@solidjs/router";
import { onMount } from "solid-js";
import { useToast } from "solid-notifications";

export default function UserMaintenanceCreatePage() {
  const pageContext = usePageContext();
  const createUserAction = useAction(CreateUserAction().action);
  const { notify } = useToast();
  const navigate = useNavigate();

  const handleSubmit = async (values: IUserModel) => {
    const res = await createUserAction({ ...values });
    if (res.ok) {
      notify("Success", { type: "success" });

      const id = (res.data as ICreateUserRes).id;
      navigate(`/maintenance/user/${id}`, { replace: true });
    }
  };

  onMount(() => {
    pageContext.action.setEditMode(true);
    pageContext.action.setBreadcrumbs(["User Maintenance", "Create"]);
  });

  return (
    <Card title="Create User">
      <UserForm action="Create" isEditMode={true} onSubmit={handleSubmit}>
        <Button label="Create" type="submit" color="primary" />
      </UserForm>
    </Card>
  );
}
