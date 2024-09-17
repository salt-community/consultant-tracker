import './page-not-found.css'
import SignIn from "../../view/sign-in/sign-in.tsx";

export const PageNotFound = () => {
  return (
    <>
      <SignIn />
      <span className="page-not-found">Page not found. Please check the url and try again.</span>
    </>
  );
};