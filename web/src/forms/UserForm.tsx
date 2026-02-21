import Button from "@/components/Button";
import FieldCheckbox from "@/components/FieldCheckbox";
import FieldInput from "@/components/FieldInput";
import {
  createForm,
  FieldValues,
  required,
  reset,
  setValues,
  submit,
  SubmitHandler,
} from "@modular-forms/solid";
import { createEffect, Match, Show, Switch } from "solid-js";

export default function UserForm(props: IUserForm) {
  const [form, { Form, Field }] = createForm<IUserModel>({ initialValues });

  createEffect(() => {
    if (props.isEditMode) return;
    if (!props.data) return;
    setValues(form, { ...props.data });
  });

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
              required={!isSearch}
              editable={isSearch}
            />
          )}
        </Field>

        <Field
          name="name"
          type="string"
          validate={!isSearch ? validator.name : []}>
          {(field, fieldProps) => (
            <FieldInput
              {...field}
              {...fieldProps}
              type="text"
              label="Name"
              required={!isSearch}
            />
          )}
        </Field>

        <div class="lg:block hidden" />

        <Show when={!isSearch}>
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
          <Match when={!isSearch}>
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
              <Button label="Edit" type="submit" color="primary" />
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
            <Button label="Search" type="submit" color="primary" />
          </Match>
        </Switch>
      </div>
    </Form>
  );
}

const validator: TValidator = {
  createBy: [],
  createDate: [],
  id: [],
  isDeleted: [],
  name: [required("Name is required")],
  roleList: [],
  updateBy: [],
  updateDate: [],
  username: [required("Username is required")],
  version: [],
};

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

type TValidator = {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  [k in keyof IUserModel]: any[];
};

interface IUserForm {
  isEditMode: boolean;
  onSubmit: SubmitHandler<IUserModel>;
  action?: "Search";
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
