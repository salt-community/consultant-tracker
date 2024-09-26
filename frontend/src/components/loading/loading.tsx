import './loading.css'
import Lottie from "react-lottie";
import animationData from "../../lotties/cat-loading.json";

export const Loading = () => {
  const defaultOptions = {
    loop: true,
    autoplay: true,
    animationData: animationData,
  };
  return (
    <div className="spinner-container">
      <Lottie
        options={defaultOptions}
        height={400}
        width={400}
      />
    </div>
  );
};
