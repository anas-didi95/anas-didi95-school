import { JSX } from "solid-js";

export default function FieldHidden(props: IFieldHidden) {
  return <input {...props} type="hidden" />;
}

interface IFieldHidden {
  name?: string;
  value?: string | number;
  onBlur?: JSX.EventHandler<HTMLInputElement | HTMLTextAreaElement, FocusEvent>;
  onChange?: JSX.EventHandler<HTMLInputElement | HTMLTextAreaElement, Event>;
  onInput?: JSX.EventHandler<
    HTMLInputElement | HTMLTextAreaElement,
    InputEvent
  >;
  ref?: (element: HTMLInputElement | HTMLTextAreaElement) => void;
}
