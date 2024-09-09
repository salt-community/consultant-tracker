import React from "react";
import Logo from "@/components/navbar/logo/logo";
import "./navbar.css";
import Link from "next/link";
import { UserButton } from "@clerk/nextjs";

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
