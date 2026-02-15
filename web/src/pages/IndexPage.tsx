import SessionUtil from "@/utils/SessionUtil";
import { Navigate } from "@solidjs/router";
import { createSignal, Show } from "solid-js";

export default function IndexPage() {
  const session = SessionUtil();
  const [hasToken] = createSignal(!!session.getSignIn());

  return (
    <Show when={hasToken()} fallback={<Navigate href="/sign-in" />}>
      <Navigate href="/dashboard" />
    </Show>
  );
}
