import {SignedIn, SignedOut} from "@clerk/clerk-react";
import LogIn from "../../view/sign-in/sign-in.tsx";
import Home from "../../view/home/home.tsx";
import {useEffect, useState} from "react";

const Authentication = () => {
  const [authorized, setAuthorized] = useState(false);
  useEffect(() => {

  }, []);
  return (
    <>
      <SignedOut><LogIn/></SignedOut>
      <SignedIn><Home/></SignedIn>
    </>
  );
};

export default Authentication;