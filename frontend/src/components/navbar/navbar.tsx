import Logo from "../../components/navbar/logo/logo";
import "./navbar.css";
import { Link } from "@mui/material";
import {UserButton} from "@clerk/clerk-react";

const Navbar = () => {
  return (
    <div className="navbar-container">
      <Link href="/">
        <Logo />
      </Link>
      <UserButton/>
    </div>
  );
};

export default Navbar;
