import "./globals.css";
import Authentication from "./components/authentication/authentication.tsx";
import {useLocation} from "react-router-dom";
import {useEffect, useState} from "react";
import {PageNotFound} from "./components/page-not-found/page-not-found.tsx";
import './App.css'

const App = ()=> {
  const location = useLocation();
  const [notFound, setNotFound] = useState(false);
  useEffect(() => {
    if(location.pathname !== "/"){
      setNotFound(true)
    }
  }, []);

  return <div className="page-container">{notFound ? <PageNotFound /> : <Authentication />}</div>
}

export default App;
