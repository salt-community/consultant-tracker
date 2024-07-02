import React from "react";
import Navbar from "@/components/navbar/navbar";

const App = ({children}: React.ReactNode) => {
  return (
    <>
      <Navbar/>
      <div className="container">
        {children}
      </div>
    </>
  );
};
export default App;