import { Component, createContext, ParentProps, useContext } from "solid-js";
import { createStore } from "solid-js/store";

interface IUserContext {
  store: IUserContextStore;
  action: IUserContextAction;
}
const UserContext = createContext<IUserContext>();

const UserProvider: Component<ParentProps> = (props) => {
  const [store, setStore] = createStore<IUserContextStore>(initialToken());

  const value: IUserContext = {
    store,
    action: {
      setToken: (newToken) => setStore(newToken),
      resetToken: () => setStore(initialToken()),
    },
  };

  return (
    <UserContext.Provider value={value}>{props.children}</UserContext.Provider>
  );
};

export default UserProvider;

export const useUserContext = () => useContext(UserContext)!;

export interface IUserContextStore {
  token: {
    access_token: string;
    refresh_token: string;
    token_type: string;
    expires_in: number;
  };
}

interface IUserContextAction {
  setToken: (token: IUserContextStore) => void;
  resetToken: () => void;
}

function initialToken(): IUserContextStore {
  return {
    token: {
      access_token: "",
      expires_in: -1,
      refresh_token: "",
      token_type: "",
    },
  };
}
