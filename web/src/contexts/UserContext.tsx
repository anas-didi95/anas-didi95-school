import { ITokenInfoRes } from "@/utils/queries/TokenInfoQuery";
import { Component, createContext, ParentProps, useContext } from "solid-js";
import { createStore } from "solid-js/store";

interface IUserContext {
  store: IUserContextStore;
  action: IUserContextAction;
}
const UserContext = createContext<IUserContext>();

const UserProvider: Component<ParentProps> = (props) => {
  const [store, setStore] = createStore<IUserContextStore>(initialUser());

  const value: IUserContext = {
    store,
    action: {
      setUser: (user) => setStore(user),
      resetUser: () => setStore(initialUser()),
    },
  };

  return (
    <UserContext.Provider value={value}>{props.children}</UserContext.Provider>
  );
};

export default UserProvider;

export const useUserContext = () => useContext(UserContext)!;

export interface IUserContextStore {
  user: ITokenInfoRes["result"];
}

interface IUserContextAction {
  setUser: (token: IUserContextStore) => void;
  resetUser: () => void;
}

function initialUser(): IUserContextStore {
  return {
    user: {
      __userId: "",
      roles: [],
      active: false,
      username: "",
      exp: -1,
      iat: -1,
      nbf: -1,
      sub: "",
      iss: "",
    },
  };
}
