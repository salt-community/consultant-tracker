import { SignIn } from "@clerk/clerk-react";

const LogIn = () => {
  return (
    <>
      <SignIn redirectUrl={"/consultant-tracker/"}/>
    </>
  );
};

export default LogIn;
