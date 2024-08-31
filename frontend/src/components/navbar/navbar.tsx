import React from 'react';
import Logo from "@/components/navbar/logo/logo";
import './navbar.css'
import Link from "next/link";
import SignOut from "@/components/navbar/sign-out/sign-out";

const Navbar = () => {
  return (
    <div className="navbar-container">
      <Link href="/"><Logo /></Link>
      <Link href="/"><SignOut /></Link>
    </div>
  );
};

export default Navbar;