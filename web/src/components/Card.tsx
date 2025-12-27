import { Component, ParentProps, Show } from "solid-js";

interface ICard {
  title?: string;
}

const Card: Component<ParentProps & ICard> = (props) => {
  return (
    <div class="card card-border rounded-box bg-base-100">
      <div class="card-body">
        <Show when={!!props.title}>
          <h2 class="card-title">{props.title}</h2>
          <hr class="mb-2" />
        </Show>
        {props.children}
      </div>
    </div>
  );
};

export default Card;
