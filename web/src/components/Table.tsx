import { debounce } from "@solid-primitives/scheduled";
import { flexRender, Table as TSTable } from "@tanstack/solid-table";
import {
  FaSolidAngleLeft,
  FaSolidAngleRight,
  FaSolidAnglesLeft,
  FaSolidAnglesRight,
  FaSolidCaretDown,
  FaSolidCaretUp,
  FaSolidSearch,
} from "solid-icons/fa";
import { For, Show } from "solid-js";
import { useToast } from "solid-notifications";

export default function Table(props: ITable) {
  const { notify } = useToast();
  const debounceGlobalFilter = debounce(
    (s: string) => props.table.setGlobalFilter(s),
    500,
  );

  return (
    <div class="overflow-x-auto rounded-box border border-base-content/5 bg-base-100 p-4">
      <div class="flex justify-end">
        <label class="input">
          <FaSolidSearch />
          <input
            type="search"
            class="grow"
            placeholder="Search"
            oninput={(e) => debounceGlobalFilter(e.target.value)}
          />
        </label>
      </div>
      <br />
      <table class="table">
        <thead>
          <For each={props.table.getHeaderGroups()}>
            {(headerGroup) => (
              <tr>
                <For each={headerGroup.headers}>
                  {(header) => (
                    <th colSpan={header.colSpan}>
                      <Show when={!header.isPlaceholder}>
                        <div
                          class={
                            header.column.getCanSort()
                              ? "cursor-pointer select-none"
                              : undefined
                          }
                          onClick={header.column.getToggleSortingHandler()}>
                          {flexRender(
                            header.column.columnDef.header,
                            header.getContext(),
                          )}
                          {{
                            asc: <FaSolidCaretUp class="ml-1 inline-block" />,
                            desc: (
                              <FaSolidCaretDown class="ml-1 inline-block" />
                            ),
                          }[header.column.getIsSorted() as string] ?? null}
                        </div>
                      </Show>
                    </th>
                  )}
                </For>
              </tr>
            )}
          </For>
        </thead>
        <tbody>
          <For each={props.table.getRowModel().rows}>
            {(row) => (
              <tr class="hover:bg-base-300">
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
          <For each={props.table.getFooterGroups()}>
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
      <div class="flex justify-end">
        <div class="join">
          <button
            class="join-item btn"
            disabled={!props.table.getCanPreviousPage()}
            onclick={() => props.table.firstPage()}>
            <FaSolidAnglesLeft class="text-xs" />
          </button>
          <button
            class="join-item btn"
            disabled={!props.table.getCanPreviousPage()}
            onclick={() => props.table.previousPage()}>
            <FaSolidAngleLeft class="text-xs" />
          </button>
          <input
            class="join-item input w-16"
            type="text"
            maxlength={
              `${props.table.getState().pagination.pageIndex + 1}`.length
            }
            value={props.table.getState().pagination.pageIndex + 1}
            onkeyup={(e) => {
              if (e.key !== "Enter") return;

              const s = e.currentTarget.value;
              if (Number.isNaN(s)) {
                notify("Enter valid page number", { type: "warning" });
                return;
              }

              const page = Number.parseInt(s);
              if (1 <= page && page <= props.table.getPageCount()) {
                props.table.setPageIndex(page - 1);
              } else {
                notify("Enter valid page number", { type: "warning" });
              }
            }}
          />
          <span class="join-item btn">
            Page of {props.table.getPageCount()}
          </span>
          <button
            class="join-item btn"
            disabled={!props.table.getCanNextPage()}
            onclick={() => props.table.nextPage()}>
            <FaSolidAngleRight class="text-xs" />
          </button>
          <button
            class="join-item btn"
            disabled={!props.table.getCanNextPage()}
            onclick={() => props.table.lastPage()}>
            <FaSolidAnglesRight class="text-xs" />
          </button>
        </div>
      </div>
    </div>
  );
}

interface ITable {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  table: TSTable<any>;
}

export interface IPagination {
  pageNo: number;
  totalRecords: number;
  totalRecordsPerPage: number;
}
