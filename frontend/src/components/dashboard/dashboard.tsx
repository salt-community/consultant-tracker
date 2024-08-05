"use client";
import Infographic from "./infographic/infographic";
import "./dashboard.css";
import {infographicData} from "@/mockData";
import FilterField from "../filter/filter";
import EnhancedTable from "../table/table";
import ViewSwitch from "@/components/view-switch/view-switch";
import GanttChart from "@/components/gantt-chart/gantt-chart";
import React, {useState, useEffect} from "react";
import {getDashboardData} from "@/api";
import {useTableContext} from "@/context/table";
import dayjs from "dayjs";

const Dashboard = () => {
  const [view, setView] = useState<string>("table");
  const tableData = useTableContext();
  const [items, setItems] = useState<any[]>([]);
  const [groups, setGroups] = useState([])

  useEffect(() => {
    getDashboardData().then((data) => {
      tableData.setData(data);
      tableData.setFilteredData(data);
    });

    fetch("http://localhost:8080/api/consultants")
      .then(res => res.json())
      .then(res => {
        const data = res.consultants.flatMap((el) => {
          return el.registeredTimeDtoList.map(item => {
            return {
              id: item.registeredTimeId,
              group: el.id,
              start_time: dayjs(item.startDate),
              end_time: dayjs(item.endDate),
              className: "",
              itemProps: {
                style: {
                  zIndex: 1,
                  background: selectColor(item.type),
                  outline: "none",
                  borderColor: selectColor(item.type),
                  borderRightWidth: "0"
                },
              },
            }
          })
        })
        setItems(data);
        return res;
      })
      .then(res => {
        const data = res.consultants.map((el) => {
          return {id: el.id, title: el.fullName}
        })
        setGroups(data);
      })
  }, []);

  const selectColor = (type: string) => {
    switch (type) {
      case "Konsult-tid":
        return "#6EACDA";
      case "Semester":
        return "#F3FEB8";
      case "Sjuk":
        return "#FFAAAA";
      case "Remaining Days":
        return "#405D72";
      default:
        return "#A0937D"
    }
  }

  return (
    <>
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
      <ViewSwitch setView={setView} view={view}/>
      {view === "timeline" && <GanttChart itemsProps={items} groupsProps={groups}/>}
      {view === "table" && <EnhancedTable/>}
    </>
  );
};

export default Dashboard;
