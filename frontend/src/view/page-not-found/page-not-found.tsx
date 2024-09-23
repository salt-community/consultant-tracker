import './page-not-found.css'
import notFoundCat from "../../assets/unauthorizedCat.webp";
import {SignOutButton} from "@clerk/clerk-react";
import Button from "@mui/material/Button";

export const PageNotFound = () => {
  return (
    <div className="not-found-container">
      <img src={notFoundCat} alt="not found cat" className="not-found-image"/>
      <div className="not-found-content-wrapper">
        <p>Page not found. Please check the url and try again.</p>
        <SignOutButton children={<Button variant="contained" color="primary">Home</Button>}/>
      </div>
    </div>
  );
};