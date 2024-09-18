import { SignIn } from "@clerk/clerk-react";

const LogIn = () => {
  return (
    <>
      <SignIn redirect="/"/>
    </>
  );
};

export default LogIn;
