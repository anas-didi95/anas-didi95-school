import { usePageContext } from "@/contexts/PageContext";
import { FieldElementProps, FieldStore } from "@modular-forms/solid";
import { Match, Show, Switch, type Component } from "solid-js";

interface IFieldText {
  label: string;
  type: "text" | "password";
  helper?: string;
  required?: boolean;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  field?: FieldStore<any, any>;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  attrs: FieldElementProps<any, any>;
}

const FieldText: Component<IFieldText> = (props) => {
  const pageContext = usePageContext();

  return (
    <fieldset class="fieldset bg-base-200 border-base-300 rounded-box border p-4">
      <legend class="fieldset-legend">
        <span>{props.label}</span>
        <Show when={props.required}>
          <span class="text-error">*</span>
        </Show>
      </legend>
      <Switch fallback={<p>Fail to render field text!</p>}>
        <Match when={pageContext.store.isEditMode}>
          <input
            {...props.attrs}
            type={props.type}
            // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
            value={props.field?.value}
            class="input w-full"
            classList={{ "input-error": !!props.field?.error }}
          />
        </Match>
        <Match when={!pageContext.store.isEditMode}>
          <p>{props.field?.value}</p>
        </Match>
      </Switch>
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
