import FieldHidden from "@/components/FieldHidden";
import FieldInput from "@/components/FieldInput";
import {
  createForm,
  custom,
  FieldValues,
  FormStore,
  required,
  setValues,
  SubmitHandler,
} from "@modular-forms/solid";
import {
  createEffect,
  createSignal,
  onMount,
  ParentProps,
  Show,
} from "solid-js";
import { createStore } from "solid-js/store";
import MetadataForm, { IMetadataModel } from "./MetadataForm";

export default function UserForm(props: IUserForm & ParentProps) {
  const [form, { Form, Field }] = createForm<IUserModel>({ initialValues });
  const [metadata, setMetadata] = createStore<IMetadataModel>();
  const [confirmPwd, setConfirmPwd] = createSignal("");

  onMount(() => {
    if (props.initForm) props.initForm(form);
  });

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

  const isUpdate = props.action === "Update";
  const isSearch = props.action === "Search";
  const isCreate = props.action === "Create";

  return (
    <Form onSubmit={props.onSubmit}>
      <fieldset
        class="grid lg:grid-cols-3 grid-cols-1 gap-6"
        disabled={form.submitting}>
        <Field
          name="username"
          type="string"
          validate={isCreate ? validator.username : []}>
          {(field, fieldProps) => (
            <FieldInput
              {...field}
              {...fieldProps}
              type="text"
              label="Username"
              required={isCreate}
              editable={isSearch || isCreate}
              isEditMode={props.isEditMode}
            />
          )}
        </Field>

        <Field
          name="name"
          type="string"
          validate={isUpdate || isCreate ? validator.name : []}>
          {(field, fieldProps) => (
            <FieldInput
              {...field}
              {...fieldProps}
              type="text"
              label="Name"
              required={isUpdate || isCreate}
              isEditMode={props.isEditMode}
            />
          )}
        </Field>

        <div class="lg:block hidden" />

        <Show when={isCreate}>
          <Field
            name="password"
            type="string"
            validate={[
              // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
              ...validator.password,
              custom((v) => v === confirmPwd(), "Password not matched"),
            ]}>
            {(field, fieldProps) => (
              <FieldInput
                {...field}
                {...fieldProps}
                isEditMode={props.isEditMode}
                label="Password"
                type="password"
                required
              />
            )}
          </Field>

          <FieldInput
            isEditMode={props.isEditMode}
            label="Confirm Password"
            type="password"
            onInput={(e) => setConfirmPwd(e.currentTarget.value)}
            required
          />
        </Show>

        <Show when={isUpdate}>
          <MetadataForm metadata={metadata} />
        </Show>

        <Field name="version" type="number">
          {(field, fieldProps) => <FieldHidden {...field} {...fieldProps} />}
        </Field>
      </fieldset>

      <div class="flex justify-end mt-4 gap-2">{props.children}</div>
    </Form>
  );
}

interface IUserForm {
  action: "Update" | "Search" | "Create";
  isEditMode: boolean;
  data?: IUserModel;
  initForm?: (o: FormStore<IUserModel>) => void;
  onSubmit?: SubmitHandler<IUserModel>;
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
  password: [required("Password is required")],
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
  password: "",
};

type TValidator = {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  [k in keyof IUserModel]: any[];
};

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
  password: string;
  roleList: string[];
}
