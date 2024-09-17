import './page-not-found.css'
import {SignIn} from "@clerk/clerk-react";

export const PageNotFound = () => {
  return (
    <>
      <SignIn />
      <span className="page-not-found">Page not found. Please check the url and try again.</span>
    </>
  );
};