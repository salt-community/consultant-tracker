import Logo from "../../components/navbar/logo/logo";
import "./navbar.css";

import { UserButton } from "@clerk/clerk-react";
import { Link } from "@mui/material";

const Navbar = () => {
  return (
    <div className="navbar-container">
      <Link href="/home">
        <Logo />
      </Link>
      <UserButton />
    </div>
  );
};

export default Navbar;
