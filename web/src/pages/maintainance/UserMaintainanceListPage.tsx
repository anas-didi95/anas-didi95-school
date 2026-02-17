import Table from "@/components/Table";
import { usePageContext } from "@/contexts/PageContext";
import GetUserListQuery, {
  IGetUserListRes,
  IUserModel,
} from "@/utils/queries/GetUserListQuery";
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
  const getUserListQuery = createAsync(() =>
    GetUserListQuery().query(page.pageIndex + 1),
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
    pageContext.action.setEditMode(false);
    pageContext.action.setBreadcrumbs(["User Maintainance"]);
  });

  return <Table table={table} />;
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
