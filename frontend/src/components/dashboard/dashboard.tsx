"use client";
import Infographic from "./infographic/infographic";
import "./dashboard.css";
import { infographicData } from "@/mockData";
import FilterField from "../filter/filter";
import EnhancedTable from "../table/table";
import ViewSwitch from "@/components/view-switch/view-switch";
import GanttChart from "@/components/gantt-chart/gantt-chart";
import { useState, useEffect } from "react";
import { getDashboardData } from "@/api";
import { useTableContext } from "@/context/table";

const Dashboard = () => {
  const [view, setView] = useState<string>("table");
  const tableData = useTableContext();

  useEffect(() => {
    getDashboardData().then((data) => {
      tableData.setData(data), tableData.setFilteredData(data);
    });
  }, []);

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
      <ViewSwitch setView={setView} view={view} />
      {view === "timeline" && <GanttChart />}
      {view === "table" && <EnhancedTable />}
    </>
  );
};

export default Dashboard;
