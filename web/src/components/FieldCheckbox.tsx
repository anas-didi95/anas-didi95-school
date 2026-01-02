import { Checkbox } from "@kobalte/core/checkbox";
import { type JSX, Show, splitProps } from "solid-js";

interface IFieldCheckbox {
  name: string;
  label: string;
  error: string;
  title?: string;
  value?: string | undefined;
  checked: boolean | undefined;
  required?: boolean | undefined;
  disabled?: boolean | undefined;
  ref: (element: HTMLInputElement) => void;
  onInput: JSX.EventHandler<HTMLInputElement, InputEvent>;
  onChange: JSX.EventHandler<HTMLInputElement, Event>;
  onBlur: JSX.EventHandler<HTMLInputElement, FocusEvent>;
}

export default function FieldCheckbox(props: IFieldCheckbox) {
  const [rootProps, inputProps] = splitProps(
    props,
    ["name", "value", "checked", "required", "disabled"],
    ["ref", "onInput", "onChange", "onBlur"],
  );

  return (
    <Checkbox
      {...rootProps}
      validationState={props.error ? "invalid" : "valid"}>
      <fieldset
        class={`${props.title ? "fieldset bg-base-200 border-base-300 rounded-box border p-4" : ""}`}>
        <Show when={!!props.title}>
          <legend class="fieldset-legend">{props.title}</legend>
        </Show>
        <label class="label">
          <input {...inputProps} type="checkbox" class="checkbox" />
          <Checkbox.Label>{props.label}</Checkbox.Label>
        </label>
      </fieldset>
    </Checkbox>
  );
}
