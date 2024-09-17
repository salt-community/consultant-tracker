import "./dashboard.css";
import FilterField from "../filter/filter";

import {Provider} from "react-redux";
import { store } from "../../store/store";
import DashboardHeader from "./dashboard-header/dashboard-header";
import GanttChart from "../gantt-chart/gantt-chart";

const Dashboard = () => {
  return (
    <Provider store={store}>
      <DashboardHeader/>
      <FilterField/>
      <GanttChart/>
    </Provider>
  );
};

export default Dashboard;