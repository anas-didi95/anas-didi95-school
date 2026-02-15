import { debounce } from "@solid-primitives/scheduled";
import { flexRender, Table as TSTable } from "@tanstack/solid-table";
import {
  FaSolidCaretDown,
  FaSolidCaretUp,
  FaSolidSearch,
} from "solid-icons/fa";
import { For, Show } from "solid-js";

export default function Table(props: ITable) {
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
    </div>
  );
}

interface ITable {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  table: TSTable<any>;
}
