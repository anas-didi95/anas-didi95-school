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
      setEditMode: (flag) =>
        setStore((prev) => ({ ...prev, isEditMode: flag })),
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
  isEditMode: boolean;
}

interface IPageContextAction {
  setBreadcrumbs: (breadcrumbs: string[]) => void;
  setEditMode: (flag: boolean) => void;
  reset: () => void;
}

function initialStore(): IPageContextStore {
  return {
    breadcrumbs: ["Dashboard"],
    isEditMode: false,
  };
}
