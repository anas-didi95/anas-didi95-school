/* @refresh reload */
import "@/index.css";
import "solid-devtools";
import { render } from "solid-js/web";

import { HashRouter, Route } from "@solidjs/router";
import { lazy } from "solid-js";
import AppLayout from "./layouts/AppLayout";

const root = document.getElementById("root");

if (import.meta.env.DEV && !(root instanceof HTMLElement)) {
  throw new Error(
    "Root element not found. Did you forget to add it to your index.html? Or maybe the id attribute got misspelled?",
  );
}

render(
  () => (
    <HashRouter root={AppLayout}>
      <Route path="/" component={lazy(() => import("@/pages/SignInPage"))} />
      {/** Authenticated Route */}
      <Route
        path="/dashboard"
        component={lazy(() => import("@/layouts/AuthenticatedLayout"))}>
        <Route component={lazy(() => import("@/pages/DashboardPage"))} />
      </Route>
      {/** Error Route */}
      <Route
        path="*404"
        component={lazy(() => import("@/pages/NotFoundPage"))}
      />
    </HashRouter>
  ),
  root!,
);
