import { Show, type Component } from "solid-js";

interface IFieldText {
  label: string;
  helper?: string;
}

const FieldText: Component<IFieldText> = ({ label, helper }) => {
  return (
    <fieldset class="fieldset bg-base-200 border-base-300 rounded-box border p-4">
      <legend class="fieldset-legend">
        {label}
        <span class="text-error">*</span>
      </legend>
      <input type="text" class="input w-full" />
      <Show when={!!helper}>
        <p class="label">{helper}</p>
      </Show>
    </fieldset>
  );
};

export default FieldText;
