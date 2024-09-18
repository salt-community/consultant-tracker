import Logo from "../../components/navbar/logo/logo";
import "./navbar.css";

import { UserButton } from "@clerk/clerk-react";
import { Link } from "@mui/material";
// import {SignOutButton} from "@clerk/clerk-react";

const Navbar = () => {
  return (
    <div className="navbar-container">
      <Link href="/">
        <Logo />
      </Link>
      {/*<SignOutButton redirectUrl={"/Consultant-Tracker"}/>*/}
      <UserButton />
    </div>
  );
};

export default Navbar;
