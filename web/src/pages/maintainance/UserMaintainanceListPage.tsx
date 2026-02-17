import Button from "@/components/Button";
import Card from "@/components/Card";
import FieldInput from "@/components/FieldInput";
import Table from "@/components/Table";
import { usePageContext } from "@/contexts/PageContext";
import GetUserListQuery, {
  IGetUserListRes,
  IUserModel,
} from "@/utils/queries/GetUserListQuery";
import { createForm, FieldValues, reset, submit } from "@modular-forms/solid";
import { A, createAsync } from "@solidjs/router";
import {
  createColumnHelper,
  createSolidTable,
  getCoreRowModel,
  getFilteredRowModel,
  getSortedRowModel,
  PaginationState,
} from "@tanstack/solid-table";
import { onMount } from "solid-js";
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
  const [form, { Form, Field }] = createForm<ISearchForm>({
    initialValues: search,
  });
  const getUserListQuery = createAsync(() =>
    GetUserListQuery().query(page.pageIndex + 1, search.username, search.name),
  );

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
    get pageCount() {
      if (!getUserListQuery()?.ok) return 0;
      const pagination = (getUserListQuery()?.data as IGetUserListRes)
        .pagination;
      return Math.ceil(
        pagination.totalRecords / pagination.totalRecordsPerPage,
      );
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
        <Form
          onSubmit={(v) => {
            setPage("pageIndex", 0);
            setSearch(v);
          }}>
          <fieldset
            class="grid lg:grid-cols-3 grid-cols-1 gap-6"
            disabled={form.submitting}>
            <Field name="username" type="string">
              {(field, props) => (
                <FieldInput
                  {...field}
                  {...props}
                  type="text"
                  label="Username"
                  required
                />
              )}
            </Field>
            <Field name="name" type="string">
              {(field, props) => (
                <FieldInput
                  {...field}
                  {...props}
                  type="text"
                  label="Name"
                  required
                />
              )}
            </Field>
          </fieldset>
          <div class="flex justify-end mt-4 gap-2">
            <Button
              label="Reset"
              type="button"
              onClick={() => {
                reset(form);
                submit(form);
              }}
            />
            <Button label="Search" type="submit" color="primary" />
          </div>
        </Form>
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
