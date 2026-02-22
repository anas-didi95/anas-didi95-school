/* @refresh reload */
import "@/index.css";
import "solid-devtools";
import { render } from "solid-js/web";

import AppLayout from "@/layouts/AppLayout";
import TokenInfoQuery from "@/utils/queries/TokenInfoQuery";
import { HashRouter, Route } from "@solidjs/router";
import { lazy } from "solid-js";

const root = document.getElementById("root");

if (import.meta.env.DEV && !(root instanceof HTMLElement)) {
  throw new Error(
    "Root element not found. Did you forget to add it to your index.html? Or maybe the id attribute got misspelled?",
  );
}

render(
  () => (
    <HashRouter root={AppLayout}>
      <Route path="/" component={lazy(() => import("@/pages/IndexPage"))} />
      <Route
        path="/sign-in"
        component={lazy(() => import("@/pages/SignInPage"))}
      />
      {/** Authenticated Route */}
      <Route
        component={lazy(() => import("@/layouts/AuthenticatedLayout"))}
        preload={() => TokenInfoQuery().query()}>
        <Route
          path="/dashboard"
          component={lazy(() => import("@/pages/DashboardPage"))}
        />
        <Route
          path="/profile"
          component={lazy(() => import("@/pages/ProfilePage"))}
        />
        <Route path="/maintenance">
          <Route
            path="/user/create"
            component={lazy(
              () => import("@/pages/maintenance/UserMaintenanceCreatePage"),
            )}
          />
          <Route
            path="/user/:id"
            component={lazy(
              () => import("@/pages/maintenance/UserMaintenancePage"),
            )}
          />
          <Route
            path="/user"
            component={lazy(
              () => import("@/pages/maintenance/UserMaintenanceListPage"),
            )}
          />
        </Route>
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
