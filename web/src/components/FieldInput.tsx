import { TextField } from "@kobalte/core/text-field";
import { type JSX, Show, splitProps } from "solid-js";

interface IFieldInput {
  isEditMode: boolean;
  name?: string;
  value?: string;
  error?: string;
  type?:
    | "text"
    | "email"
    | "tel"
    | "password"
    | "url"
    | "date"
    | "datetime-local";
  label?: string;
  placeholder?: string;
  multiline?: boolean;
  required?: boolean;
  disabled?: boolean;
  editable?: boolean;
  ref?: (element: HTMLInputElement | HTMLTextAreaElement) => void;
  onInput?: JSX.EventHandler<
    HTMLInputElement | HTMLTextAreaElement,
    InputEvent
  >;
  onChange?: JSX.EventHandler<HTMLInputElement | HTMLTextAreaElement, Event>;
  onBlur?: JSX.EventHandler<HTMLInputElement | HTMLTextAreaElement, FocusEvent>;
}

export default function FieldInput(props: IFieldInput) {
  const [rootProps, inputProps] = splitProps(
    props,
    ["name", "value", "required", "disabled"],
    ["placeholder", "ref", "onInput", "onChange", "onBlur"],
  );

  return (
    <TextField
      {...rootProps}
      validationState={props.error ? "invalid" : "valid"}>
      <fieldset class="fieldset bg-base-200 border-base-300 rounded-box border p-4 h-full">
        <legend class="fieldset-legend">
          <TextField.Label>
            <span>{props.label}&nbsp;</span>
            <Show when={props.required}>
              <span class="text-error">&nbsp;*</span>
            </Show>
          </TextField.Label>
        </legend>
        <Show
          when={(props.editable ?? true) && props.isEditMode}
          fallback={
            <p>
              {isDateValue(props.type, props.value)
                ? new Date(props.value!).toLocaleString()
                : props.value}
            </p>
          }>
          <Show
            when={props.multiline}
            fallback={
              <TextField.Input
                {...inputProps}
                type={props.type}
                value={
                  isDateValue(props.type, props.value)
                    ? props.value!.split(".")[0]
                    : props.value
                }
                class="input w-full"
                classList={{ "input-error": !!props.error }}
              />
            }>
            <TextField.TextArea
              {...inputProps}
              autoResize
              value={props.value}
              class="input w-full"
              classList={{ "input-error": !!props.error }}
            />
          </Show>
        </Show>
        <TextField.ErrorMessage>
          <span class="label text-error text-sm">{props.error}</span>
        </TextField.ErrorMessage>
      </fieldset>
    </TextField>
  );
}

function isDateValue(type?: string, value?: string) {
  return !!type && ["date", "datetime-local"].includes(type) && !!value;
}
