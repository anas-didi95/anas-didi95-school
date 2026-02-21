import FieldCheckbox from "@/components/FieldCheckbox";
import FieldInput from "@/components/FieldInput";
import { Show } from "solid-js";

export default function MetadataForm(props: IMetadataForm) {
  return (
    <>
      <Show when={typeof props.metadata.lastModifiedBy === "string"}>
        <FieldInput
          isEditMode={false}
          label="Last Modified By"
          type="text"
          value={props.metadata.lastModifiedBy}
        />
      </Show>
      <Show when={typeof props.metadata.lastModifiedDate === "string"}>
        <FieldInput
          isEditMode={false}
          label="Last Modified Date"
          type="datetime-local"
          value={props.metadata.lastModifiedDate}
        />
      </Show>
      <Show when={typeof props.metadata.version === "number"}>
        <FieldInput
          isEditMode={false}
          label="Version"
          type="number"
          value={`${props.metadata.version}`}
        />
      </Show>
      <Show when={typeof props.metadata.isDeleted === "boolean"}>
        <FieldCheckbox
          isEditMode={false}
          label="Is Deleted?"
          title="Status"
          value={props.metadata.isDeleted}
        />
      </Show>
    </>
  );
}

interface IMetadataForm {
  metadata: IMetadataModel;
}

export interface IMetadataModel {
  lastModifiedBy?: string;
  lastModifiedDate?: string;
  version?: number;
  isDeleted?: boolean;
}
