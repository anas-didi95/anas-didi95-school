import { Component, ParentProps } from "solid-js";
import { ToastProvider, Toaster } from "solid-notifications";

const AppLayout: Component<ParentProps> = (props) => (
  <ToastProvider>
    <Toaster />
    <main class="min-h-screen flex flex-col bg-base-300">
      {props.children}
      <footer class="footer sm:footer-horizontal footer-center bg-base-300 text-base-content p-4">
        <aside>
          <p>
            Copyright © {new Date().getFullYear()} - All right reserved by Anas
            Juwaidi
          </p>
        </aside>
      </footer>
    </main>
  </ToastProvider>
);

export default AppLayout;
