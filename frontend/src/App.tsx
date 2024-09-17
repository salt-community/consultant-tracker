// import { useUser } from "@clerk/clerk-react";
import "./globals.css";
import Home from "./view/home/home";
// import LogIn from "./view/sign-in/sign-in";

const App = ()=> {

  // const { user } = useUser();
  // return <>{!user ? <LogIn /> : <Home />}</>;
  return <Home/>
}

export default App;
