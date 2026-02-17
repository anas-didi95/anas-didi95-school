import Button from "@/components/Button";
import FieldCheckbox from "@/components/FieldCheckbox";
import FieldInput from "@/components/FieldInput";
import { usePageContext } from "@/contexts/PageContext";
import {
  createForm,
  FieldValues,
  reset,
  setValues,
  submit,
  SubmitHandler,
} from "@modular-forms/solid";
import { createEffect, Match, Show, Switch } from "solid-js";

export default function UserForm(props: IUserForm) {
  const pageContext = usePageContext();
  const [form, { Form, Field }] = createForm<IUserModel>({ initialValues });

  createEffect(() => {
    if (pageContext.store.isEditMode) return;
    if (!props.data) return;
    setValues(form, { ...props.data });
  });

  const isUpdate = props.action === "Update";
  const isSearch = props.action === "Search";

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
              required={isUpdate}
              editable={isSearch}
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
              required={isUpdate}
            />
          )}
        </Field>

        <div class="lg:block hidden" />

        <Show when={isUpdate}>
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
        </Show>
      </fieldset>

      <div class="flex justify-end mt-4 gap-2">
        <Switch>
          <Match when={isUpdate}>
            <Show
              when={props.isEditMode}
              fallback={
                <Show when={!!props.onEdit}>
                  <Button
                    label="Edit"
                    type="button"
                    color="primary"
                    onClick={props.onEdit}
                  />
                </Show>
              }>
              <Show when={!!props.onCancel}>
                <Button label="Cancel" type="button" onClick={props.onCancel} />
              </Show>
            </Show>
          </Match>
          <Match when={isSearch}>
            <Button
              label="Reset"
              type="button"
              onClick={() => {
                reset(form);
                submit(form);
              }}
            />
          </Match>
        </Switch>
        <Button label={props.action} type="submit" color="primary" />
      </div>
    </Form>
  );
}

const initialValues: IUserModel = {
  name: "",
  createBy: "",
  createDate: "",
  id: "",
  isDeleted: false,
  roleList: [],
  updateBy: "",
  updateDate: "",
  username: "",
  version: 0,
};

interface IUserForm {
  action: "Update" | "Search";
  isEditMode: boolean;
  onSubmit: SubmitHandler<IUserModel>;
  onEdit?: () => void;
  onCancel?: () => void;
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
