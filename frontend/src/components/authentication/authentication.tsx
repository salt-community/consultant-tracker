import {SignedIn, SignedOut} from "@clerk/clerk-react";
import LogIn from "../../view/sign-in/sign-in.tsx";
import Home from "../../view/home/home.tsx";

const Authentication = () => {
  return (
    <>
      <SignedOut><LogIn/></SignedOut>
      <SignedIn><Home/></SignedIn>
    </>
  );
};

export default Authentication;