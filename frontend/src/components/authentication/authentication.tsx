import {SignedIn, SignedOut, useAuth, useUser} from "@clerk/clerk-react";
import {LogIn, Home, Unauthorized} from "../../view";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {RootState} from "../../store/store";
import {getAuthorization} from "../../api";
import {
  setAuthorized,
  setRole,
  setShowContent,
  setUser,
} from "../../store/slices/AuthorizationSlice";
import {Loading} from "../loading";
import {template} from "../../constants.ts";

export const Authentication = () => {
  const dispatch = useDispatch();
  const authorized = useSelector(
    (state: RootState) => state.authorization.authorized
  );
  const showContent = useSelector(
    (state: RootState) => state.authorization.showContent
  );

  const {getToken} = useAuth();
  const user = useUser();

  const getAccessToken = async () => {
    let token: string | null = "";
    token = await getToken({template});
    console.log("Token: " + token)
    if (token === null) {
      dispatch(setAuthorized(false));
      return;
    }
    if (user && user.user && user.user.fullName) {
      dispatch(setUser(user.user.fullName));
    }
    getAuthorization(token).then((response) => {
        if (response.status === 403 || response.status === 401) {
          dispatch(setAuthorized(false))
          return;
        } else {
          dispatch(setAuthorized(true));
          return response.text()
        }
      }
    ).then(res => res &&dispatch(setRole(res)))
      .finally(() => {
        dispatch(setShowContent(true))
      });
  };

  useEffect(() => {
    void getAccessToken();
  }, [user]);

  return (
    <>
      <SignedOut>
        <LogIn/>
      </SignedOut>
      <SignedIn>
        {showContent ? authorized ? <Home/> : <Unauthorized/> : <Loading/>}
      </SignedIn>
    </>
  );
};

