// import "./infographic.css";
import "./infographic-v1.css";

type Props = {
  title: string;
  amount: number;
  variant: string;
};

const Infographic = ({ title, amount, variant }: Props) => {
  return (
    <div className={`infographic-container ${variant}`}>
      <h5>{title}:</h5>
      <span className="infographic-amount" >{amount}</span>
    </div>
  );
};

export default Infographic;
