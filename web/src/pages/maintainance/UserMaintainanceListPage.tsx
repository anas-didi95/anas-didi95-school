import Button from "@/components/Button";
import Card from "@/components/Card";
import Table from "@/components/Table";
import { usePageContext } from "@/contexts/PageContext";
import UserForm, { IUserModel } from "@/forms/UserForm";
import GetUserListQuery, {
  IGetUserListRes,
} from "@/utils/queries/ListUserQuery";
import { FieldValues, FormStore, reset, submit } from "@modular-forms/solid";
import { A, createAsync } from "@solidjs/router";
import {
  createColumnHelper,
  createSolidTable,
  getCoreRowModel,
  getFilteredRowModel,
  getSortedRowModel,
  PaginationState,
} from "@tanstack/solid-table";
import { createSignal, onMount } from "solid-js";
import { createStore } from "solid-js/store";

export default function UserMaintainancePage() {
  const pageContext = usePageContext();
  const [page, setPage] = createStore<PaginationState>({
    pageIndex: 0,
    pageSize: 10,
  });
  const [search, setSearch] = createStore<ISearchForm>({
    name: "",
    username: "",
  });
  const getUserListQuery = createAsync(() =>
    GetUserListQuery().query(page.pageIndex + 1, search.username, search.name),
  );
  const [form, setForm] = createSignal<FormStore<IUserModel>>();

  const table = createSolidTable({
    get data() {
      if (!getUserListQuery()?.ok) return [];
      return (getUserListQuery()?.data as IGetUserListRes).resultList;
    },
    columns: tableColumns,
    getCoreRowModel: getCoreRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    globalFilterFn: "includesString",
    manualPagination: true,
    get rowCount() {
      if (!getUserListQuery()?.ok) return 0;
      return (getUserListQuery()?.data as IGetUserListRes).pagination
        .totalRecords;
    },
    state: {
      get pagination() {
        if (!getUserListQuery()?.ok) return { pageIndex: 0, pageSize: 10 };
        const pagination = (getUserListQuery()?.data as IGetUserListRes)
          .pagination;
        return {
          pageIndex: pagination.pageNo - 1,
          pageSize: pagination.totalRecordsPerPage,
        };
      },
    },
    onPaginationChange: setPage,
  });

  onMount(() => {
    pageContext.action.setEditMode(true);
    pageContext.action.setBreadcrumbs(["User Maintainance"]);
  });

  return (
    <>
      <Card title="Search User">
        <UserForm
          isEditMode={true}
          action="Search"
          onSubmit={(v) => {
            setPage("pageIndex", 0);
            setSearch(v);
          }}
          initForm={setForm}>
          <>
            <Button
              label="Reset"
              type="button"
              onClick={() => {
                if (form()) {
                  reset(form()!);
                  submit(form()!);
                }
              }}
            />
            <Button label="Search" type="submit" color="primary" />
          </>
        </UserForm>
      </Card>
      <br />
      <Table table={table} />
    </>
  );
}

const columnHelper = createColumnHelper<IUserModel>();

const tableColumns = [
  columnHelper.accessor((row) => `${row.id}|${row.username}`, {
    id: "username",
    header: "Username",
    cell: (o) => {
      const [id, username] = o.getValue().split("|");
      return (
        <A href={`/maintenance/user/${id}`} class="text-primary">
          {username}
        </A>
      );
    },
  }),
  columnHelper.accessor("name", { header: "Name" }),
  columnHelper.accessor((row) => row.updateBy ?? row.createBy, {
    id: "lastModifiedBy",
    header: "Last Modified By",
    enableGlobalFilter: false,
  }),
  columnHelper.accessor(
    (row) => new Date(row.updateDate ?? row.createDate).toLocaleString(),
    {
      id: "lastModifiedDate",
      header: "Last Modified Date",
      enableGlobalFilter: false,
    },
  ),
];

interface ISearchForm extends FieldValues {
  username: string;
  name: string;
}
