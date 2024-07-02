"use client";
import { navLinks } from "@/mockData";
import "./hamburger-menu.css";
import { useState } from "react";
import { IoCloseOutline } from "react-icons/io5";
import { RxHamburgerMenu } from "react-icons/rx";

const HamburgerMenu = () => {
  const [open, setOpen] = useState(false);
  const toggleMenu = () => {
    setOpen(!open);
  };

  return (
    <>
      <button
        className={open ? "hidden" : "navbar-hamburger__button visible"}
        onClick={toggleMenu}
      >
        <RxHamburgerMenu className="navbar-hamburger__icon" />
      </button>
      <div
        className={open ? "navbar-menu__overlay visible" : "hidden"}
        onClick={toggleMenu}
      ></div>
      <div className={open ? "navbar-list__wrapper opened" : "hidden"}>
        <IoCloseOutline
          onClick={toggleMenu}
          className="navbar-list__close-icon"
        />
        <ul className="navbar-list">
          {navLinks.map((link, index) => {
            return <li key={index}>{link}</li>;
          })}
        </ul>
      </div>
    </>
  );
};

export default HamburgerMenu;
