import {PiSignOutFill} from "react-icons/pi";
import "./sign-out.css"
import {SignOutButton} from "@clerk/clerk-react";

const SignOut = () => {
  return (
    <SignOutButton redirectUrl="/sign-in">
      <PiSignOutFill className="sign-out-icon"/>
    </SignOutButton>
  );
};

export default SignOut;