"use client"
import "./dashboard.css";
import FilterField from "../filter/filter";
import GanttChart from "@/components/gantt-chart/gantt-chart";
import DashboardHeader from "@/components/dashboard/dashboard-header/dashboard-header";
import {store} from "@/store/store";
import {Provider} from "react-redux";

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