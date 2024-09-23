import './loading.css'
import shaker from "../../assets/shaker.gif"

const Loading = () => {
  return (
    <div className="spinner-container">
      <img src={shaker} />
    {/* <CircularProgress disableShrink /> */}
    </div>
  );
};

export default Loading;