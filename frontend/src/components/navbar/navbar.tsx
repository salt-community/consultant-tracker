import Logo from "../../components/navbar/logo/logo";
import "./navbar.css";

import {SignOutButton} from "@clerk/clerk-react";
import { Link } from "@mui/material";

const Navbar = () => {
  return (
    <div className="navbar-container">
      <Link href="/">
        <Logo />
      </Link>
      <SignOutButton/>
    </div>
  );
};

export default Navbar;
