import SignOutAction from "@/utils/actions/SignOutAction";
import TokenInfoQuery, { ITokenInfoRes } from "@/utils/queries/TokenInfoQuery";
import SessionUtil from "@/utils/SessionUtil";
import { createAsync, Navigate, useAction, useNavigate } from "@solidjs/router";
import { FaRegularUser } from "solid-icons/fa";
import { TbLayoutSidebarLeftExpand } from "solid-icons/tb";
import { Component, Match, ParentProps, Switch } from "solid-js";
import { useToast } from "solid-notifications";

const DashboardPage: Component<ParentProps> = (props) => {
  const tokenInfoQuery = createAsync(() => TokenInfoQuery().query());
  const signOut = useAction(SignOutAction().action);
  const navigate = useNavigate();
  const { notify } = useToast();
  const session = SessionUtil();

  const handleSignOut = async () => {
    const res = await signOut();
    if (res.ok) {
      session.clear();
      notify("User sign-out success", { type: "success" });
      navigate("/", { replace: true });
    }
  };

  return (
    <Switch fallback={<div class="skeleton h-32 w-32"></div>}>
      <Match when={!!tokenInfoQuery() && !tokenInfoQuery()?.ok}>
        <Navigate href="/" />
      </Match>
      <Match when={!!tokenInfoQuery() && tokenInfoQuery()?.ok}>
        <section class="drawer lg:drawer-open">
          <input id="my-drawer-4" type="checkbox" class="drawer-toggle" />
          <div class="drawer-content min-h-screen">
            {/*<!-- Navbar -->*/}
            <div class="navbar bg-base-300 px-4">
              <div class="navbar-start">
                <label
                  for="my-drawer-4"
                  aria-label="open sidebar"
                  class="btn btn-square btn-ghost">
                  {/*<!-- Sidebar toggle icon -->*/}
                  <TbLayoutSidebarLeftExpand class="my-1.5 inline-block size-4" />
                </label>
              </div>
              <div class="navbar-center">
                <a class="btn btn-ghost text-xl">Dashboard</a>
              </div>
              <div class="navbar-end">
                <div class="dropdown dropdown-end">
                  <div
                    tabindex="0"
                    role="button"
                    class="btn btn-ghost btn-circle avatar">
                    <FaRegularUser class="my-1.5 inline-block size-4 rounded-full" />
                  </div>
                  <ul
                    tabindex="-1"
                    class="menu menu-sm dropdown-content bg-base-100 rounded-box z-1 mt-3 w-52 p-2 shadow">
                    <li>
                      <p class="font-bold">
                        Hi,{" "}
                        {
                          (tokenInfoQuery()?.data as ITokenInfoRes).result
                            .username
                        }
                      </p>
                    </li>
                    <li>
                      <a class="justify-between">
                        Profile
                        <span class="badge">New</span>
                      </a>
                    </li>
                    <li>
                      <a>Settings</a>
                    </li>
                    <li onClick={() => void handleSignOut()}>
                      <a>Logout</a>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            {/*<!-- Page content here -->*/}
            <div class="p-4">{props.children}</div>
          </div>

          <div class=" drawer-side is-drawer-close:overflow-visible">
            <label
              for="my-drawer-4"
              aria-label="close sidebar"
              class="drawer-overlay"></label>
            <div class="flex min-h-full flex-col items-start bg-base-200 is-drawer-close:w-14 is-drawer-open:w-64">
              {/*<!-- Sidebar content here -->*/}
              <ul class="menu w-full grow">
                {/*<!-- List item -->*/}
                <li>
                  <button
                    class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                    data-tip="Homepage">
                    {/*<!-- Home icon -->*/}
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 24 24"
                      stroke-linejoin="round"
                      stroke-linecap="round"
                      stroke-width="2"
                      fill="none"
                      stroke="currentColor"
                      class="my-1.5 inline-block size-4">
                      <path d="M15 21v-8a1 1 0 0 0-1-1h-4a1 1 0 0 0-1 1v8"></path>
                      <path d="M3 10a2 2 0 0 1 .709-1.528l7-5.999a2 2 0 0 1 2.582 0l7 5.999A2 2 0 0 1 21 10v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                    </svg>
                    <span class="is-drawer-close:hidden">Homepage</span>
                  </button>
                </li>

                {/*<!-- List item -->*/}
                <li>
                  <button
                    class="is-drawer-close:tooltip is-drawer-close:tooltip-right"
                    data-tip="Settings">
                    {/*<!-- Settings icon -->*/}
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 24 24"
                      stroke-linejoin="round"
                      stroke-linecap="round"
                      stroke-width="2"
                      fill="none"
                      stroke="currentColor"
                      class="my-1.5 inline-block size-4">
                      <path d="M20 7h-9"></path>
                      <path d="M14 17H5"></path>
                      <circle cx="17" cy="17" r="3"></circle>
                      <circle cx="7" cy="7" r="3"></circle>
                    </svg>
                    <span class="is-drawer-close:hidden">Settings</span>
                  </button>
                </li>
              </ul>
            </div>
          </div>
        </section>
      </Match>
    </Switch>
  );
};

export default DashboardPage;
