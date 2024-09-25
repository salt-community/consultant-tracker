import {SignedIn, SignedOut, useAuth, useSession, useUser} from "@clerk/clerk-react";
import LogIn from "../../view/sign-in/sign-in.tsx";
import Home from "../../view/home/home.tsx";
import {setToken} from "../../store/slices/TokenSlice.ts";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {RootState} from "../../store/store.ts";
import {getAuthorization} from "../../api.ts";
import {setAuthorized, setShowContent, setUser} from "../../store/slices/AuthorizationSlice.ts";
import Unauthorized from "../../view/unauthorized/unauthorized.tsx";
import Loading from "../loading/loading.tsx";
import {fromEpochToDate} from "../../utils/utils.ts";

const Authentication = () => {
  const dispatch = useDispatch();
  const authorized = useSelector((state: RootState) => state.authorization.authorized)
  const showContent = useSelector((state: RootState) => state.authorization.showContent)
  const token = useSelector((state: RootState) => state.token.token)

  const {getToken} = useAuth();
  const user = useUser();
  const session = useSession();
  let expirationToken = new Date();



  const fetchToken = async () => {
    const template = 'email_test'
    const token = await getToken({template})
    if(session.session && session.session.lastActiveToken && session.session.lastActiveToken.jwt){
      expirationToken = fromEpochToDate(session.session.lastActiveToken.jwt.claims.exp);
    }
    user && user.user && user.user.fullName && dispatch(setUser(user.user.fullName))
    token && dispatch(setToken(token));
  };
  const fetchAuthorized = () => {
    getAuthorization(token).then(response => response.status === 403 || response.status === 401
      ? dispatch(setAuthorized(false))
      : dispatch(setAuthorized(true)))
      .finally(()=>dispatch(setShowContent(true)))
  }

  useEffect(() => {
    fetchToken();
    token != "" && fetchAuthorized()
  }, [token]);


  return (
    <>
      <SignedOut>
        <LogIn/>
      </SignedOut>
      <SignedIn>
        {showContent ? authorized ? <Home/> : <Unauthorized/> : <Loading />}
      </SignedIn>
    </>
  );
};

export default Authentication;
