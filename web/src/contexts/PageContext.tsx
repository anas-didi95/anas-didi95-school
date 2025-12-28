import { Component, createContext, ParentProps, useContext } from "solid-js";
import { createStore } from "solid-js/store";

interface IPageContext {
  store: IPageContextStore;
  action: IPageContextAction;
}
const PageContext = createContext<IPageContext>();

const PageProvider: Component<ParentProps> = (props) => {
  const [store, setStore] = createStore<IPageContextStore>(initialStore());

  const value: IPageContext = {
    store,
    action: {
      setBreadcrumbs: (breadcrumbs) =>
        setStore((prev) => ({ ...prev, breadcrumbs })),
      reset: () => setStore(initialStore),
    },
  };

  return (
    <PageContext.Provider value={value}>{props.children}</PageContext.Provider>
  );
};

export default PageProvider;

export const usePageContext = () => useContext(PageContext)!;

export interface IPageContextStore {
  breadcrumbs: string[];
}

interface IPageContextAction {
  setBreadcrumbs: (breadcrumbs: string[]) => void;
  reset: () => void;
}

function initialStore(): IPageContextStore {
  return {
    breadcrumbs: ["Dashboard"],
  };
}
