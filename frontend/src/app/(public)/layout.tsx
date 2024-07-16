import { ReactNode } from "react";
import Navbar from "@/components/navbar/navbar";
import './layout.css'

const RootLayout = ({ children }: Readonly<{ children: ReactNode }>) => {
  return (
    <>
      <Navbar />
      <div className="page-wrapper">{children}</div>
    </>
  );
};

export default RootLayout;
