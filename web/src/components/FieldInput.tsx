import { usePageContext } from "@/contexts/PageContext";
import { TextField } from "@kobalte/core/text-field";
import { type JSX, Show, splitProps } from "solid-js";

interface IFieldInput {
  name: string;
  value: string | undefined;
  error: string;
  type?: "text" | "email" | "tel" | "password" | "url" | "date" | undefined;
  label?: string | undefined;
  placeholder?: string | undefined;
  multiline?: boolean | undefined;
  required?: boolean | undefined;
  disabled?: boolean | undefined;
  ref: (element: HTMLInputElement | HTMLTextAreaElement) => void;
  onInput: JSX.EventHandler<HTMLInputElement | HTMLTextAreaElement, InputEvent>;
  onChange: JSX.EventHandler<HTMLInputElement | HTMLTextAreaElement, Event>;
  onBlur: JSX.EventHandler<HTMLInputElement | HTMLTextAreaElement, FocusEvent>;
}

export default function FieldInput(props: IFieldInput) {
  const [rootProps, inputProps] = splitProps(
    props,
    ["name", "value", "required", "disabled"],
    ["placeholder", "ref", "onInput", "onChange", "onBlur"],
  );
  const pageContext = usePageContext();

  return (
    <TextField
      {...rootProps}
      validationState={props.error ? "invalid" : "valid"}>
      <fieldset class="fieldset bg-base-200 border-base-300 rounded-box border p-4">
        <legend class="fieldset-legend">
          <TextField.Label>
            <span>{props.label}&nbsp;</span>
            <Show when={props.required}>
              <span class="text-error">&nbsp;*</span>
            </Show>
          </TextField.Label>
        </legend>
        <Show
          when={pageContext.store.isEditMode}
          fallback={<p>{props.value}</p>}>
          <Show
            when={props.multiline}
            fallback={
              <TextField.Input
                {...inputProps}
                type={props.type}
                value={props.value}
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
          <span class="label text-error">{props.error}</span>
        </TextField.ErrorMessage>
      </fieldset>
    </TextField>
  );
}
