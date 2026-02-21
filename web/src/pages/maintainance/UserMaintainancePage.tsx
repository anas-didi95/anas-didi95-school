import { useParams } from "@solidjs/router";

export default function UserMaintainancePage() {
  const params = useParams();

  return <div>UserMaintainancePage : {params.id}</div>;
}
