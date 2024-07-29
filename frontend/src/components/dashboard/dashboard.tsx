'use client'
import Infographic from "./infographic/infographic";
import "./dashboard.css";
import { infographicData } from "@/mockData";
import FilterField from "../filter/filter";
import EnhancedTable from "../table/table";
import * as React from "react";
import ViewSwitch from "@/components/view-switch/view-switch";
import GanttChart from "@/components/gantt-chart/gantt-chart";
import {useState} from "react";

const Dashboard = () => {
  const [view, setView] = useState<string>("table");
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
      <ViewSwitch  setView={setView} view={view}/>
      {view === "timeline" && <GanttChart/>}
      {view === "table" && <EnhancedTable />}
    </>
  );
};

export default Dashboard;
