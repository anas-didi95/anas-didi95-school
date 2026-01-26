import Button from "@/components/Button";
import Card from "@/components/Card";
import FieldCheckbox from "@/components/FieldCheckbox";
import FieldInput from "@/components/FieldInput";
import { usePageContext } from "@/contexts/PageContext";
import UpdateUserAction from "@/utils/actions/UpdateUserAction";
import GetUserQuery, { IGetUserRes } from "@/utils/queries/GetUserQuery";
import TokenInfoQuery, { ITokenInfoRes } from "@/utils/queries/TokenInfoQuery";
import {
  createForm,
  FieldValues,
  setValues,
  SubmitHandler,
} from "@modular-forms/solid";
import { createAsync, useAction } from "@solidjs/router";
import { createEffect, createSignal, onMount, Show } from "solid-js";

export default function ProfilePage() {
  const [userId, setUserId] = createSignal("");
  const pageContext = usePageContext();
  const tokenInfoQuery = createAsync(() => TokenInfoQuery().query());
  const getUserQuery = createAsync(() => GetUserQuery().query(userId()));
  const [form, { Form, Field }] = createForm<IProfileForm>();
  const updateUserAction = useAction(
    UpdateUserAction([GetUserQuery().key]).action,
  );

  const handleSubmit: SubmitHandler<IProfileForm> = async (values) => {
    const res = await updateUserAction(userId(), { ...values.result });
    if (res.ok) {
      pageContext.action.setEditMode(false);
    }
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

    const values = getUserQuery()?.data as IGetUserRes;
    pageContext.action.setBreadcrumbs(["Profile", values.result.name]);

    setValues(form, { ...values });
  });

  return (
    <Card title={`${pageContext.store.isEditMode ? "Edit" : "View"} Profile`}>
      <Form onSubmit={handleSubmit}>
        <fieldset
          class="grid lg:grid-cols-3 grid-cols-1 gap-6"
          disabled={form.submitting}>
          <Field name="result.username" type="string">
            {(field, props) => (
              <FieldInput
                {...field}
                {...props}
                type="text"
                label="Username"
                required
                editable={false}
              />
            )}
          </Field>
          <Field name="result.name" type="string">
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
          <div class="lg:block hidden" />
          <Field name="result.isDeleted" type="boolean">
            {(field, props) => (
              <FieldCheckbox
                {...field}
                {...props}
                label="Is Deleted?"
                title="Status"
              />
            )}
          </Field>
          <Field name="result.updateBy" type="string">
            {(field, props) => (
              <FieldInput
                {...field}
                {...props}
                type="text"
                label="Update By"
                required
                editable={false}
              />
            )}
          </Field>
          <Field name="result.updateDate" type="string">
            {(field, props) => (
              <FieldInput
                {...field}
                {...props}
                type="datetime-local"
                label="Update Date"
                required
                editable={false}
              />
            )}
          </Field>
          <Field name="result.version" type="number">
            {(field, props) => (
              <FieldInput
                {...field}
                {...props}
                value={"" + field.value}
                label="Version"
                required
                editable={false}
              />
            )}
          </Field>
          <Field name="result.createBy" type="string">
            {(field, props) => (
              <FieldInput
                {...field}
                {...props}
                type="text"
                label="Create By"
                required
                editable={false}
              />
            )}
          </Field>
          <Field name="result.createDate" type="string">
            {(field, props) => (
              <FieldInput
                {...field}
                {...props}
                type="datetime-local"
                label="Create Date"
                required
                editable={false}
              />
            )}
          </Field>
        </fieldset>
        <div class="flex justify-end mt-4 gap-2">
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
        </div>
      </Form>
    </Card>
  );
}

interface IProfileForm extends FieldValues, IGetUserRes {}
