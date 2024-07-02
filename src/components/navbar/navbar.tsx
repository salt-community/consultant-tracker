import React from 'react';
import Logo from "@/components/navbar/logo/logo";
import './navbar.css'
import HamburgerMenu from "@/components/navbar/hamburger-menu/hamburger-menu";

const Navbar = () => {
  return (
    <div className="navbar-container">
      <Logo />
      <HamburgerMenu />
    </div>
  );
};

export default Navbar;