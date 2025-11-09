import { FieldElementProps, FieldStore } from "@modular-forms/solid";
import { Show, type Component } from "solid-js";

interface IFieldText {
  label: string;
  helper?: string;
  required?: boolean;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  field?: FieldStore<any, any>;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  attrs: FieldElementProps<any, any>;
}

const FieldText: Component<IFieldText> = (props) => {
  return (
    <fieldset class="fieldset bg-base-200 border-base-300 rounded-box border p-4">
      <legend class="fieldset-legend">
        <span>{props.label}</span>
        <Show when={props.required}>
          <span class="text-error">*</span>
        </Show>
      </legend>
      <input
        {...props.attrs}
        type="text"
        class="input w-full"
        classList={{ "input-error": !!props.field?.error }}
      />
      <Show when={!!props.field?.error}>
        <p class="label text-error">{props.field?.error}</p>
      </Show>
      <Show when={!!props.helper}>
        <p class="label">{props.helper}</p>
      </Show>
    </fieldset>
  );
};

export default FieldText;
