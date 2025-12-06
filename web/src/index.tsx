/* @refresh reload */
import "@/index.css";
import "solid-devtools";
import { render } from "solid-js/web";

import { HashRouter, RouteDefinition } from "@solidjs/router";
import { lazy } from "solid-js";
import { ToastProvider, Toaster } from "solid-notifications";

const root = document.getElementById("root");

if (import.meta.env.DEV && !(root instanceof HTMLElement)) {
  throw new Error(
    "Root element not found. Did you forget to add it to your index.html? Or maybe the id attribute got misspelled?",
  );
}

render(
  () => (
    <ToastProvider>
      <Toaster />
      <HashRouter>{getRoutes()}</HashRouter>
    </ToastProvider>
  ),
  root!,
);

function getRoutes(): RouteDefinition[] {
  return [{ path: "/", component: lazy(() => import("@/App")) }];
}
