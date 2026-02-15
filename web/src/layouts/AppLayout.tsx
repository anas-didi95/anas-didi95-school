import PageProvider from "@/contexts/PageContext";
import { ParentProps } from "solid-js";
import { ToastProvider, Toaster } from "solid-notifications";

export default function AppLayout(props: ParentProps) {
  return (
    <PageProvider>
      <ToastProvider>
        <Toaster />
        <main class="min-h-screen flex flex-col bg-base-300">
          {props.children}
          <footer class="footer sm:footer-horizontal footer-center text-base-content p-4">
            <aside>
              <p>
                Copyright © {new Date().getFullYear()} - All right reserved by
                Anas Juwaidi
              </p>
            </aside>
          </footer>
        </main>
      </ToastProvider>
    </PageProvider>
  );
}
