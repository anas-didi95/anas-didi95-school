import { usePageContext } from "@/contexts/PageContext";
import { onMount } from "solid-js";

export default function UserMaintainancePage() {
  const pageContext = usePageContext();

  onMount(() => {
    pageContext.action.setEditMode(false);
    pageContext.action.setBreadcrumbs(["User Maintainance"]);
  });

  return <div>UserMaintainancePage</div>;
}
