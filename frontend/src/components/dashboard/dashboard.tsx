"use client";
import Infographic from "./infographic/infographic";
import "./dashboard.css";
import {consultantItems, infographicData} from "@/mockData";
import FilterField from "../filter/filter";
import EnhancedTable from "../table/table";
import ViewSwitch from "@/components/view-switch/view-switch";
import GanttChart from "@/components/gantt-chart/gantt-chart";
import { useState, useEffect } from "react";
import { getDashboardData } from "@/api";
import { useTableContext } from "@/context/table";
import dayjs from "dayjs";
import {ConsultantItemsType} from "@/types";

const Dashboard = () => {
  const [view, setView] = useState<string>("table");
  const tableData = useTableContext();
  const [items, setItems] = useState<ConsultantItemsType[]>([]);
  const [groups, setGroups] = useState([])

  useEffect(() => {
    getDashboardData().then((data) => {
      tableData.setData(data), tableData.setFilteredData(data);
    });
    fetch("http://localhost:8080/api/consultants")
      .then(res=> res.json())
      .then(res => {
        const data = res.consultants.map(el => {
          return el.registeredTimeDtoList.map(item => {
            return {
              id: 1,
              group: 1,
              start_time: dayjs("2024-07-22T00:00:00"),
              end_time: dayjs("2024-08-22T23:59:59"),
              itemProps: {
                style: {
                  zIndex: 5,
                  background: selectColor(item.type),
                  outline: "none",
                  border: "none"
                },
              },
            }
          })
        })
        console.log(data)
        setItems(data);
        return res;
      })
      .then(res => {
        const data = res.consultants.map(el => {
          return {id: 1, title: el.fullName}
        })
        console.log('groups', data)
        setGroups(data);
      })
  }, []);
  const selectColor = (type: string) => {
    switch (type) {
      case "Konsult-tid":
        return "#29B269";
      case "Semester":
        return "yellow";
      case "Sjuk":
        return "green";
      case "Remaining Days":
        return "gray";
      default:
        return "red"
    }
  }

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
      {view === "timeline" && <GanttChart items={items} groups={groups}/>}
      {view === "table" && <EnhancedTable />}
    </>
  );
};

export default Dashboard;
