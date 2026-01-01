import Card from "@/components/Card";
import FieldText from "@/components/FieldText";
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
import { Component, createEffect, createSignal, onMount } from "solid-js";

const ProfilePage: Component = () => {
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
          <Field name="name">
            {(field, attrs) => (
              <FieldText
                type="text"
                label="Name"
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
  name: string;
}
