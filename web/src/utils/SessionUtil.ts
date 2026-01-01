import { ISignInRes } from "@/utils/actions/SignInAction";

const SessionUtil = () => {
  const SIGN_IN_KEY = "__SGN_IN#_KEY";

  const putSignIn = (token: ISignInRes) =>
    sessionStorage.setItem(SIGN_IN_KEY, JSON.stringify(token));

  const getSignIn = () => {
    const token = sessionStorage.getItem(SIGN_IN_KEY);

    if (!token) {
      return null;
    }
    return JSON.parse(token) as ISignInRes;
  };

  const clear = () => sessionStorage.clear();

  return { putSignIn, getSignIn, clear };
};

export default SessionUtil;
