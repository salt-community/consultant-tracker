import { SignedIn, SignedOut, useAuth } from "@clerk/clerk-react";
import LogIn from "../../view/sign-in/sign-in.tsx";
import Home from "../../view/home/home.tsx";
import { setToken } from "../../store/slices/TokenSlice.ts";
import { useDispatch } from "react-redux";
import { useEffect } from "react";

const Authentication = () => {
  const dispatch = useDispatch();

  const { getToken } = useAuth();

  const fetchToken = async () => {
    const template = 'email_test'
    const token = await getToken({ template })
    // const token = await getToken();
    console.log(token);
    token && dispatch(setToken(token));
  };

  useEffect(() => {
    fetchToken();
  }, []);

  return (
    <>
      <SignedOut>
        <LogIn />
      </SignedOut>
      <SignedIn>
        <Home />
      </SignedIn>
    </>
  );
};

export default Authentication;
