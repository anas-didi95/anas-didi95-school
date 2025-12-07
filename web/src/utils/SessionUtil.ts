import { ISignInRes } from "./actions/SignInAction";

const SessionUtil = () => {
  const TOKEN_KEY = "__TOKEN_KEY";

  const putToken = (token: ISignInRes) =>
    sessionStorage.setItem(TOKEN_KEY, JSON.stringify(token));

  const getToken = () => {
    const token = sessionStorage.getItem(TOKEN_KEY);

    if (!token) {
      return null;
    }
    return JSON.parse(token) as ISignInRes;
  };

  return { putToken, getToken };
};

export default SessionUtil;
