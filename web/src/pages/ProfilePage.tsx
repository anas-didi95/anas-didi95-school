import Card from "@/components/Card";
import { usePageContext } from "@/contexts/PageContext";
import TokenInfoQuery, { ITokenInfoRes } from "@/utils/queries/TokenInfoQuery";
import { createAsync } from "@solidjs/router";
import { Component, createEffect } from "solid-js";

const ProfilePage: Component = () => {
  const pageContext = usePageContext();
  const tokenInfoQuery = createAsync(() => TokenInfoQuery().query());

  createEffect(() => {
    if (tokenInfoQuery()?.ok) {
      pageContext.action.setBreadcrumbs([
        "Profile",
        (tokenInfoQuery()?.data as ITokenInfoRes).result.username,
      ]);
    }
  });

  return (
    <Card title="TITLE">
      <div class="container">
        <span>HELLO</span>
      </div>
    </Card>
  );
};

export default ProfilePage;
