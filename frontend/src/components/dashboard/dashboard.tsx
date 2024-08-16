"use client";
import Infographic from "./infographic/infographic";
import "./dashboard.css";
import {infographicData} from "@/mockData";
import FilterField from "../filter/filter";
import EnhancedTable from "../table/table";
import ViewSwitch from "@/components/view-switch/view-switch";
import GanttChart from "@/components/gantt-chart/gantt-chart";
import React, {useState, useEffect} from "react";
import {getConsultantsData, getDashboardData} from "@/api";
import {useTableContext} from "@/context/table";
import dayjs from "dayjs";
import {encodeString, mapConsultantsToCalendarItems, selectColor} from "@/utils/utils";
import {
  AllClientsAndResponsiblePtResponse,
  ConsultantFetchType,
  RegisteredTimeItemType,
} from "@/types";

const Dashboard = () => {
  const [view, setView] = useState<string>("timeline");
  const tableData = useTableContext();
  const [items, setItems] = useState<any[]>([]);
  const [groups, setGroups] = useState([]);
  const [listOfResponsiblePt, setListOfResponsiblePts] = useState<string[]>([]);
  const [listOfClients, setListOfClients] = useState<string[]>([]);
  const [totalItems, setTotalItems] = useState<number>(0);
  const [filterPts, setFilterPts] = useState(["Josefin Stål"]);
  const [filterClients, setFilterClients] = useState<string[]>([]);
  const [filterName, setFilterName] = useState<string>("");
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);

  useEffect(() => {
    fetch("http://localhost:8080/api/consultants/getAllClientsAndPts")
      .then((res) => res.json())
      .then((res: AllClientsAndResponsiblePtResponse) => {
        setListOfResponsiblePts(res.pts);
        setListOfClients(res.clients);
      });
  }, []);

  const fetchConsultantsData = () => {
    const filterPtsEncodeUriString = encodeString(filterPts, "pt")
    const filterClientsEncodeUriString = encodeString(filterClients, "client")
    getConsultantsData(page, rowsPerPage, filterClientsEncodeUriString, filterPtsEncodeUriString, filterName)
      .then((res) => {
        setItems(mapConsultantsToCalendarItems(res));
        setTotalItems(res.totalConsultants);
        const data = res.consultants.map((el: ConsultantFetchType) => {
          const title = el.country === "NO" ? el.fullName + ` (${el.country})` : el.fullName;
          return {id: el.id, title: title};
        });
        setGroups(data);
        tableData.setData(res);
      })
  }

  useEffect(() => {
    const delayDebounceFn = setTimeout(() => {
      fetchConsultantsData();
    }, 500)
    return () => clearTimeout(delayDebounceFn)
  }, [filterName]);

  useEffect(() => {
    fetchConsultantsData();
  }, [filterPts, filterClients, page, rowsPerPage]);

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
      <FilterField
        lisOfResponsiblePt={listOfResponsiblePt}
        listOfClients={listOfClients}
        filterPts={filterPts}
        filterClients={filterClients}
        setFilterPts={setFilterPts}
        setFilterClients={setFilterClients}
        setFilterName={setFilterName}
        filterName={filterName}
      />
      <ViewSwitch setView={setView} view={view}/>
      {view === "timeline" && (
        <GanttChart
          itemsProps={items}
          groupsProps={groups}
          totalItems={totalItems}
          setPage={setPage}
          page={page}
          rowsPerPage={rowsPerPage}
          setRowsPerPage={setRowsPerPage}
        />
      )}
      {view === "table" && <EnhancedTable totalItems={totalItems}
                                          setPage={setPage}
                                          page={page}
                                          rowsPerPage={rowsPerPage}
                                          setRowsPerPage={setRowsPerPage}/>}
    </>
  );
};

export default Dashboard;
