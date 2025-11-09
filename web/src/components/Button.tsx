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

const Button: Component<IButton> = ({ label, color, onClick, type }) => {
  return (
    <button
      classList={{
        "btn-primary": color === "primary",
        "btn-secondary": color === "secondary",
        "btn-accent": color === "accent",
        "btn-info": color === "info",
        "btn-success": color === "success",
        "btn-warning": color === "warning",
        "btn-error": color === "error",
      }}
      class="btn"
      onClick={onClick}
      type={type}>
      {label}
    </button>
  );
};

export default Button;
