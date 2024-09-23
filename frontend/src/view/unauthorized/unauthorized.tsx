import './unauthorized.css'
import {SignOutButton} from "@clerk/clerk-react";
import Button from "@mui/material/Button";
import unauthorizedImage from "../../assets/unauthorizedImage.png"

const Unauthorized = () => {
  return (
    <div className="unauthorized-container">
      <img src={unauthorizedImage} alt="unauthorized cat" className="unauthorized-image"/>
      <div className="unauthorized-content-wrapper">
        <p>It seems like your account is not authorized to see this content.</p>
        <p>If you need access contact Salt admin and try again.</p>
      </div>
      <SignOutButton children={<Button variant="contained" color="primary">Try again</Button>}/>

    </div>
  );
};

export default Unauthorized;