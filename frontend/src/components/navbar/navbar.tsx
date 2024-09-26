import "./navbar.css";
import Logo from "./logo/logo";
import { Link } from "@mui/material";
import {UserButton} from "@clerk/clerk-react";

export const Navbar = () => {
  return (
    <div className="navbar-container">
      <Link href="/">
        <Logo />
      </Link>
      <UserButton/>
    </div>
  );
};

