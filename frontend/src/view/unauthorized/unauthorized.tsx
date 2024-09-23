import './unauthorized.css'
import {SignOutButton} from "@clerk/clerk-react";
import Button from "@mui/material/Button";
import unauthorizedCat from "../../assets/unauthorizedCat.webp"

const Unauthorized = () => {
  return (
    <div className="unauthorized-container">
      <img src={unauthorizedCat} alt="unauthorized cat" className="unauthorized-image"/>
      <div className="unauthorized-content-wrapper">
        <p>It seems like your account is not authorized to see this content.</p>
        <p>If you need access contact Salt admin and try again.</p>
        <SignOutButton children={<Button variant="contained" color="primary">Try again</Button>}/>
      </div>

    </div>
  );
};

export default Unauthorized;