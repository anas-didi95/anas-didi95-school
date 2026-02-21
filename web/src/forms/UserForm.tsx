import FieldHidden from "@/components/FieldHidden";
import FieldInput from "@/components/FieldInput";
import {
  createForm,
  FieldValues,
  FormStore,
  required,
  setValues,
  SubmitHandler,
} from "@modular-forms/solid";
import { createEffect, onMount, ParentProps, Show } from "solid-js";
import { createStore } from "solid-js/store";
import MetadataForm, { IMetadataModel } from "./MetadataForm";

export default function UserForm(props: IUserForm & ParentProps) {
  const [form, { Form, Field }] = createForm<IUserModel>({ initialValues });
  const [metadata, setMetadata] = createStore<IMetadataModel>();

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

        <Field name="version" type="number">
          {(field, fieldProps) => <FieldHidden {...field} {...fieldProps} />}
        </Field>

        <div class="lg:block hidden" />

        <Show when={!isSearch}>
          <MetadataForm metadata={metadata} />
        </Show>
      </fieldset>

      <div class="flex justify-end mt-4 gap-2">{props.children}</div>
    </Form>
  );
}

interface IUserForm {
  isEditMode: boolean;
  action?: "Search";
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
