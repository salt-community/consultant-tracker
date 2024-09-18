import Logo from "../../components/navbar/logo/logo";
import "./navbar.css";

import { UserButton } from "@clerk/clerk-react";
import { Link } from "@mui/material";

const Navbar = () => {
  return (
    <div className="navbar-container">
      <Link href="/">
        <Logo />
      </Link>
      <Link href="/"><UserButton /></Link>
    </div>
  );
};

export default Navbar;
