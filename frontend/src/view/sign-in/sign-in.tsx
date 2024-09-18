import { SignIn } from "@clerk/clerk-react";

const LogIn = () => {
  return (
    <>
      <SignIn path={"/"}/>
    </>
  );
};

export default LogIn;
