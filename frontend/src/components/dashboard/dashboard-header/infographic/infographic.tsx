import "./infographic.css";

type Props = {
  title: string;
  amount: number;
  variant: string;
};

const Infographic = ({ title, amount, variant }: Props) => {
  return (
    <div className={`infographic-container ${variant}`}>
      <h5>{title}:</h5>
      <span>{amount}</span>
    </div>
  );
};

export default Infographic;
