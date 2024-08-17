import './loading.css'
import {CircularProgress} from "@mui/material";

const Loading = () => {
  return (
    <div className="spinner-container">
    <CircularProgress disableShrink />
    </div>
  );
};

export default Loading;