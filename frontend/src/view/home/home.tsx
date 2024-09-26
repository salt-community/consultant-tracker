import {Navbar, Dashboard} from "../../components";
import "./home.css";
import {useEffect, useState} from "react";
import { BsArrowUp } from "react-icons/bs";

export const Home = () => {
  const [isVisible, setIsVisible] = useState(false);
  const handleScrollToTop = () => {
    window.scrollTo({top: 0, behavior: "smooth"});
  }
  const toggleVisibility = () => {
    if (window.scrollY > 300) {
      setIsVisible(true);
    } else {
      setIsVisible(false);
    }
  };
  useEffect(() => {
    window.addEventListener('scroll', toggleVisibility);
    return () => {
      window.removeEventListener('scroll', toggleVisibility);
    };
  }, []);
  return (
    <>
      <button onClick={handleScrollToTop} className={isVisible ? "button-scroll-up" : "button-scroll-up hidden"}>
        <BsArrowUp/>
      </button>
      <Navbar/>
      <div className="dashboard-wrapper">
        <Dashboard/>
      </div>
    </>
  );
};
