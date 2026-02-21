import Button from "@/components/Button";
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
import { createStore } from "solid-js/store";
import MetadataForm, { IMetadataModel } from "./MetadataForm";

export default function UserForm(props: IUserForm) {
  const [form, { Form, Field }] = createForm<IUserModel>({ initialValues });
  const [metadata, setMetadata] = createStore<IMetadataModel>();

  createEffect(() => {
    if (props.isEditMode) return;
    if (!props.data) return;

    setValues(form, { ...props.data });
    setMetadata({
      isDeleted: props.data.isDeleted,
      lastModifiedBy: props.data.updateBy ?? props.data.createBy,
      lastModifiedDate: props.data.updateDate ?? props.data.createDate,
      version: props.data.version,
    });
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
              isEditMode={props.isEditMode}
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
              isEditMode={props.isEditMode}
            />
          )}
        </Field>

        <div class="lg:block hidden" />

        <Show when={!isSearch}>
          <MetadataForm metadata={metadata} />
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
              <Button label="Update" type="submit" color="primary" />
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
