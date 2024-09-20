import { SignIn } from "@clerk/clerk-react";

const LogIn = () => {
  return (
    <>
      <SignIn redirectUrl={"/Consultant-Tracker/"}/>
    </>
  );
};

export default LogIn;
