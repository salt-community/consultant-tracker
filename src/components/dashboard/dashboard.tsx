import Infographic from "./infographic/infographic";
import "./dashboard.css";
import {infographicData} from "@/mockData";
import FilterField from "../filter/filter";
import PageWrapper from "@/components/page-wrapper/page-wrapper";
import TableLegend from "@/components/table/table-legend/table-legend";
import Table from "../table/table";
import EnhancedTable from "../table/table-mui";

const Dashboard = () => {
  return (
    <PageWrapper>
      <div className="dashboard-infographic__card">
        {infographicData.map((element, index) => {
          const {title, amount, variant} = element;
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
      <FilterField/>
      <TableLegend />
      {/* <Table/> */}
      <EnhancedTable />
    </PageWrapper>
  );
};

export default Dashboard;
