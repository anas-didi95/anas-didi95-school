import { Component, JSX } from "solid-js";

interface IButton {
  label: string;
  type: "button" | "menu" | "reset" | "submit";
  color?:
    | "primary"
    | "secondary"
    | "accent"
    | "info"
    | "success"
    | "warning"
    | "error";
  onClick?: JSX.EventHandlerUnion<
    HTMLButtonElement,
    MouseEvent,
    JSX.EventHandler<HTMLButtonElement, MouseEvent>
  >;
}

const Button: Component<IButton> = (props) => {
  return (
    <button
      classList={{
        "btn-primary": props.color === "primary",
        "btn-secondary": props.color === "secondary",
        "btn-accent": props.color === "accent",
        "btn-info": props.color === "info",
        "btn-success": props.color === "success",
        "btn-warning": props.color === "warning",
        "btn-error": props.color === "error",
      }}
      class="btn"
      onClick={props.onClick}
      type={props.type}>
      {props.label}
    </button>
  );
};

export default Button;
