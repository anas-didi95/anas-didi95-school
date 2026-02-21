import { Checkbox } from "@kobalte/core/checkbox";
import { FaRegularSquare, FaRegularSquareCheck } from "solid-icons/fa";
import { type JSX, Show, splitProps } from "solid-js";

interface IFieldCheckbox {
  isEditMode: boolean;
  name?: string;
  label?: string;
  value?: boolean;
  error?: string;
  title?: string;
  checkedValue?: string;
  required?: boolean;
  disabled?: boolean;
  editable?: boolean;
  ref?: (element: HTMLInputElement) => void;
  onInput?: JSX.EventHandler<HTMLInputElement, InputEvent>;
  onChange?: JSX.EventHandler<HTMLInputElement, Event>;
  onBlur?: JSX.EventHandler<HTMLInputElement, FocusEvent>;
}

export default function FieldCheckbox(props: IFieldCheckbox) {
  const [rootProps, inputProps] = splitProps(
    props,
    ["name", "value", "checkedValue", "required", "disabled"],
    ["ref", "onInput", "onChange", "onBlur"],
  );

  return (
    <Checkbox
      {...rootProps}
      validationState={props.error ? "invalid" : "valid"}
      value={props.checkedValue}>
      <fieldset
        class={`${props.title ? "fieldset bg-base-200 border-base-300 rounded-box border p-4 h-full" : ""}`}>
        <Show when={!!props.title}>
          <legend class="fieldset-legend">{props.title}</legend>
        </Show>
        <Show
          when={(props.editable ?? true) && props.isEditMode}
          fallback={
            <div class="flex items-center">
              <Show when={props.value} fallback={<FaRegularSquare />}>
                <FaRegularSquareCheck />
              </Show>
              <span class="ml-2">{props.label}</span>
            </div>
          }>
          <label class="label">
            <input
              {...inputProps}
              checked={props.value}
              disabled={props.isEditMode}
              type="checkbox"
              class="checkbox checkbox-primary"
            />
            <Checkbox.Label>{props.label}</Checkbox.Label>
          </label>
        </Show>
      </fieldset>
    </Checkbox>
  );
}
