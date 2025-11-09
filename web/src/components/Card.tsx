import { Component, ParentProps, Show } from "solid-js";

interface ICard {
  title?: string;
}

const Card: Component<ParentProps & ICard> = ({ children, title }) => {
  return (
    <div class="card card-border rounded-box bg-base-100">
      <div class="card-body">
        <Show when={!!title}>
          <h2 class="card-title">{title}</h2>
          <hr class="mb-2" />
        </Show>
        {children}
      </div>
    </div>
  );
};

export default Card;
