import Infographic from "./infographic/infographic";
import "./dashboard.css";
import { infographicData } from "@/mockData";

const Dashboard = () => {
  return (
    <section >
      <div className="dashboard-infographic__card">
        {infographicData.map((element, index) => {
          const { title, amount, variant } = element;
          return <Infographic key={index} title={title} amount={amount} variant={variant} />;
        })}
      </div>
    </section>
  );
};

export default Dashboard;
