import { SignedIn, SignedOut, useAuth, useUser } from "@clerk/clerk-react";
import LogIn from "../../view/sign-in/sign-in";
import Home from "../../view/home/home";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { RootState } from "../../store/store";
import { getAuthorization } from "../../api";
import {
  setAuthorized,
  setShowContent,
  setUser,
} from "../../store/slices/AuthorizationSlice";
import Unauthorized from "../../view/unauthorized/unauthorized";
import Loading from "../loading/loading";
import { template } from "../../constants.js";

const Authentication = () => {
  const dispatch = useDispatch();
  const authorized = useSelector(
    (state: RootState) => state.authorization.authorized
  );
  const showContent = useSelector(
    (state: RootState) => state.authorization.showContent
  );

  const { getToken, signOut } = useAuth();
  const user = useUser();

  const fetchAuthorized = (token: string) => {
    getAuthorization(token)
      .then((response) =>
        response.status === 403 || response.status === 401
          ? dispatch(setAuthorized(false))
          : dispatch(setAuthorized(true))
      )
      .finally(() => dispatch(setShowContent(true)));
  };

  const getAccesstoken = async () => {
    let token: string | null = "";
    token = await getToken({ template });
    console.log("token", token);
    if (user && user.user && user.user.fullName) {
      dispatch(setUser(user.user.fullName));
    }
    if (!token) {
      dispatch(setAuthorized(false));
      return;
    }
    fetchAuthorized(token);
  };

  useEffect(() => {
    getAccesstoken();
  }, []);

  return (
    <>
      <SignedOut>
        <LogIn />
      </SignedOut>
      <SignedIn>
        {showContent ? authorized ? <Home /> : <Unauthorized /> : <Loading />}
      </SignedIn>
    </>
  );
};

export default Authentication;
