import Card from "@/components/Card";
import { FieldInput } from "@/components/FieldInput";
import { usePageContext } from "@/contexts/PageContext";
import GetUserQuery, { IGetUserRes } from "@/utils/queries/GetUserQuery";
import TokenInfoQuery, { ITokenInfoRes } from "@/utils/queries/TokenInfoQuery";
import {
  createForm,
  FieldValues,
  setValues,
  SubmitHandler,
} from "@modular-forms/solid";
import { createAsync } from "@solidjs/router";
import { createEffect, createSignal, onMount } from "solid-js";

export default function ProfilePage() {
  const [userId, setUserId] = createSignal("");
  const pageContext = usePageContext();
  const tokenInfoQuery = createAsync(() => TokenInfoQuery().query());
  const getUserQuery = createAsync(() => GetUserQuery().query(userId()));
  const [form, { Form, Field }] = createForm<IProfileForm>();

  const handleSubmit: SubmitHandler<IProfileForm> = (values) => {
    console.log("values", values);
  };

  onMount(() => {
    pageContext.action.setEditMode(false);
  });

  createEffect(() => {
    if (!tokenInfoQuery()?.ok) {
      return;
    }

    const result = (tokenInfoQuery()?.data as ITokenInfoRes).result;
    setUserId(result.__userId);
  });

  createEffect(() => {
    if (!getUserQuery()?.ok) {
      return;
    }

    const result = (getUserQuery()?.data as IGetUserRes).result;
    pageContext.action.setBreadcrumbs(["Profile", result.name]);

    const values: IProfileForm = {
      username: result.username,
      name: result.name,
    };
    setValues(form, values);
  });

  return (
    <Card title="View Profile">
      <Form onSubmit={handleSubmit}>
        <fieldset class="grid grid-cols-3 gap-6" disabled={form.submitting}>
          <Field name="username">
            {(field, props) => (
              <FieldInput
                {...field}
                {...props}
                type="text"
                label="Username"
                required
              />
            )}
          </Field>
          <Field name="name">
            {(field, props) => (
              <FieldInput
                {...field}
                {...props}
                type="text"
                label="Name"
                required
              />
            )}
          </Field>
        </fieldset>
        <button onClick={() => pageContext.action.setEditMode(true)}>
          Edit
        </button>
      </Form>
    </Card>
  );
}

interface IProfileForm extends FieldValues {
  username: string;
  name: string;
}
