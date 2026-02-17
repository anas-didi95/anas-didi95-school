// components/UserForm.tsx
import Button from "@/components/Button";
import FieldCheckbox from "@/components/FieldCheckbox";
import FieldInput from "@/components/FieldInput";
import { usePageContext } from "@/contexts/PageContext";
import {
  createForm,
  FieldValues,
  setValues,
  SubmitHandler,
} from "@modular-forms/solid";
import { createEffect, Show } from "solid-js";

export default function UserForm(props: IUserForm) {
  const pageContext = usePageContext();
  const [form, { Form, Field }] = createForm<IUserModel>();

  createEffect(() => {
    if (pageContext.store.isEditMode) return;
    if (!props.data) return;
    setValues(form, { ...props.data });
  });

  return (
    <Form onSubmit={props.onSubmit}>
      <fieldset
        class="grid lg:grid-cols-3 grid-cols-1 gap-6"
        disabled={form.submitting}>
        <Field name="username" type="string">
          {(field, fieldProps) => (
            <FieldInput
              {...field}
              {...fieldProps}
              type="text"
              label="Username"
              required
              editable={false}
            />
          )}
        </Field>

        <Field name="name" type="string">
          {(field, fieldProps) => (
            <FieldInput
              {...field}
              {...fieldProps}
              type="text"
              label="Name"
              required
            />
          )}
        </Field>

        <div class="lg:block hidden" />

        <Field name="isDeleted" type="boolean">
          {(field, fieldProps) => (
            <FieldCheckbox
              {...field}
              {...fieldProps}
              label="Is Deleted?"
              title="Status"
            />
          )}
        </Field>

        <Field name="updateBy" type="string">
          {(field, fieldProps) => (
            <FieldInput
              {...field}
              {...fieldProps}
              type="text"
              label="Update By"
              required
              editable={false}
            />
          )}
        </Field>

        <Field name="updateDate" type="string">
          {(field, fieldProps) => (
            <FieldInput
              {...field}
              {...fieldProps}
              type="datetime-local"
              label="Update Date"
              required
              editable={false}
            />
          )}
        </Field>

        <Field name="version" type="number">
          {(field, fieldProps) => (
            <FieldInput
              {...field}
              {...fieldProps}
              value={"" + field.value}
              label="Version"
              required
              editable={false}
            />
          )}
        </Field>

        <Field name="createBy" type="string">
          {(field, fieldProps) => (
            <FieldInput
              {...field}
              {...fieldProps}
              type="text"
              label="Create By"
              required
              editable={false}
            />
          )}
        </Field>

        <Field name="createDate" type="string">
          {(field, fieldProps) => (
            <FieldInput
              {...field}
              {...fieldProps}
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
          when={props.isEditMode}
          fallback={
            <Button
              label="Edit"
              type="button"
              color="primary"
              onClick={props.onEdit}
            />
          }>
          <Button label="Cancel" type="button" onClick={props.onCancel} />
          <Button label="Update" type="submit" color="primary" />
        </Show>
      </div>
    </Form>
  );
}

interface IUserForm {
  isEditMode: boolean;
  onSubmit: SubmitHandler<IUserModel>;
  onEdit: () => void;
  onCancel: () => void;
  data?: IUserModel;
}

export interface IUserModel extends FieldValues {
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
}
