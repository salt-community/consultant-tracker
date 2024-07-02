import { ReactNode } from "react";
import Navbar from "@/components/navbar/navbar";

const RootLayout = ({ children }: Readonly<{ children: ReactNode }>) => {
  return (
    <>
      <Navbar />
      <div className="container">{children}</div>
    </>
  );
};

export default RootLayout;
