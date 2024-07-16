import Infographic from "./infographic/infographic";
import "./dashboard.css";
import { infographicData } from "@/mockData";
import FilterField from "../filter/filter";
import EnhancedTable from "../table/table";

const Dashboard = () => {
  return (
    <>
      <div className="dashboard-infographic__card">
        {infographicData.map((element, index) => {
          const { title, amount, variant } = element;
          return (
            <Infographic
              key={index}
              title={title}
              amount={amount}
              variant={variant}
            />
          );
        })}
      </div>
      <FilterField />
      <EnhancedTable />
    </>
  );
};

export default Dashboard;
