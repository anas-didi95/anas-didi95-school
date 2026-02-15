import { usePageContext } from "@/contexts/PageContext";
import {
  ColumnDef,
  createSolidTable,
  flexRender,
  getCoreRowModel,
} from "@tanstack/solid-table";
import { createSignal, For, onMount } from "solid-js";

export default function UserMaintainancePage() {
  const pageContext = usePageContext();
  const [data, setData] = createSignal(defaultData);
  const rerender = () => setData(defaultData);

  const table = createSolidTable({
    get data() {
      return data();
    },
    columns: defaultColumns,
    getCoreRowModel: getCoreRowModel(),
  });

  onMount(() => {
    pageContext.action.setEditMode(false);
    pageContext.action.setBreadcrumbs(["User Maintainance"]);
  });

  return (
    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100">
      <table class="table">
        {" "}
        <thead>
          <For each={table.getHeaderGroups()}>
            {(headerGroup) => (
              <tr>
                <For each={headerGroup.headers}>
                  {(header) => (
                    <th>
                      {header.isPlaceholder
                        ? null
                        : flexRender(
                            header.column.columnDef.header,
                            header.getContext(),
                          )}
                    </th>
                  )}
                </For>
              </tr>
            )}
          </For>
        </thead>
        <tbody>
          <For each={table.getRowModel().rows}>
            {(row) => (
              <tr>
                <For each={row.getVisibleCells()}>
                  {(cell) => (
                    <td>
                      {flexRender(
                        cell.column.columnDef.cell,
                        cell.getContext(),
                      )}
                    </td>
                  )}
                </For>
              </tr>
            )}
          </For>
        </tbody>
        <tfoot>
          <For each={table.getFooterGroups()}>
            {(footerGroup) => (
              <tr>
                <For each={footerGroup.headers}>
                  {(header) => (
                    <th>
                      {header.isPlaceholder
                        ? null
                        : flexRender(
                            header.column.columnDef.footer,
                            header.getContext(),
                          )}
                    </th>
                  )}
                </For>
              </tr>
            )}
          </For>
        </tfoot>
      </table>
      <div class="h-4" />
      <button onClick={() => rerender()} class="border p-2">
        Rerender
      </button>
    </div>
  );
}

interface Person {
  firstName: string;
  lastName: string;
  age: number;
  visits: number;
  status: string;
  progress: number;
}

const defaultData: Person[] = [
  {
    firstName: "tanner",
    lastName: "linsley",
    age: 24,
    visits: 100,
    status: "In Relationship",
    progress: 50,
  },
  {
    firstName: "tandy",
    lastName: "miller",
    age: 40,
    visits: 40,
    status: "Single",
    progress: 80,
  },
  {
    firstName: "joe",
    lastName: "dirte",
    age: 45,
    visits: 20,
    status: "Complicated",
    progress: 10,
  },
];

const defaultColumns: ColumnDef<Person>[] = [
  {
    accessorKey: "firstName",
    cell: (info) => info.getValue(),
    footer: (info) => info.column.id,
  },
  {
    accessorFn: (row) => row.lastName,
    id: "lastName",
    cell: (info) => <i>{info.getValue<string>()}</i>,
    header: () => <span>Last Name</span>,
    footer: (info) => info.column.id,
  },
  {
    accessorKey: "age",
    header: () => "Age",
    footer: (info) => info.column.id,
  },
  {
    accessorKey: "visits",
    header: () => <span>Visits</span>,
    footer: (info) => info.column.id,
  },
  {
    accessorKey: "status",
    header: "Status",
    footer: (info) => info.column.id,
  },
  {
    accessorKey: "progress",
    header: "Profile Progress",
    footer: (info) => info.column.id,
  },
];
