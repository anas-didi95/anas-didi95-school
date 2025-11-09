import { Component } from "solid-js";

interface IButton {
  label: string;
  color?:
    | "primary"
    | "secondary"
    | "accent"
    | "info"
    | "success"
    | "warning"
    | "error";
}

const Button: Component<IButton> = ({ label, color }) => {
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
      class="btn">
      {label}
    </button>
  );
};

export default Button;
