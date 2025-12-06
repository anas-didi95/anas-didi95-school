import { Component, ParentProps } from "solid-js";
import { ToastProvider, Toaster } from "solid-notifications";

const AppLayout: Component<ParentProps> = (props) => (
  <ToastProvider>
    <Toaster />
    <main class="bg-base-300">{props.children}</main>
  </ToastProvider>
);

export default AppLayout;
