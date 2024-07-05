import React from 'react';
import Logo from "@/components/navbar/logo/logo";
import './navbar.css'
import HamburgerMenu from "@/components/navbar/hamburger-menu/hamburger-menu";
import Link from "next/link";

const Navbar = () => {
  return (
    <div className="navbar-container">
      <Link href="/"><Logo /></Link>
      <HamburgerMenu />
    </div>
  );
};

export default Navbar;