import Card from "@/components/Card";
import FieldText from "@/components/FieldText";
import { usePageContext } from "@/contexts/PageContext";
import TokenInfoQuery, { ITokenInfoRes } from "@/utils/queries/TokenInfoQuery";
import {
  createForm,
  FieldValues,
  setValues,
  SubmitHandler,
} from "@modular-forms/solid";
import { createAsync } from "@solidjs/router";
import { Component, createEffect, onMount } from "solid-js";

const ProfilePage: Component = () => {
  const pageContext = usePageContext();
  const tokenInfoQuery = createAsync(() => TokenInfoQuery().query());
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
    pageContext.action.setBreadcrumbs(["Profile", result.username]);

    const values: IProfileForm = {
      username: result.username,
    };
    setValues(form, values);
  });

  return (
    <Card title="View Profile">
      <Form onSubmit={handleSubmit}>
        <fieldset class="grid grid-cols-3 gap-6" disabled={form.submitting}>
          <Field name="username">
            {(field, attrs) => (
              <FieldText
                type="text"
                label="Username"
                field={field}
                attrs={attrs}
                required
              />
            )}
          </Field>
        </fieldset>
      </Form>
    </Card>
  );
};

export default ProfilePage;

interface IProfileForm extends FieldValues {
  username: string;
}
