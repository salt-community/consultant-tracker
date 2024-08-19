"use client";
import Infographic from "./infographic/infographic";
import "./dashboard.css";
import {infographicData} from "@/mockData";
import FilterField from "../filter/filter";
import GanttChart from "@/components/gantt-chart/gantt-chart";
import React, {useState, useEffect} from "react";
import {getConsultantsData} from "@/api";
import {useTableContext} from "@/context/table";
import {encodeString, mapConsultantsToCalendarItems} from "@/utils/utils";
import {
  AllClientsAndResponsiblePtResponse,
  ConsultantFetchType,
} from "@/types";

const Dashboard = () => {
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
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
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
    setLoading(true);
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
        setLoading(false);
      })
      .catch(err=>  {
        setLoading(false)
        setError("Failed to fetch resources")
      }
    )
  }

  useEffect(() => {
    const delayDebounceFn = setTimeout(() => {
      setPage(0)
      fetchConsultantsData();
    }, 500)
    return () => clearTimeout(delayDebounceFn)
  }, [filterName]);

  useEffect(() => {
    setPage(0)
    fetchConsultantsData();
  }, [filterPts, filterClients]);


  useEffect(() => {
    fetchConsultantsData();
  }, [page, rowsPerPage]);

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
        <GanttChart
          itemsProps={items}
          groupsProps={groups}
          totalItems={totalItems}
          setPage={setPage}
          page={page}
          rowsPerPage={rowsPerPage}
          setRowsPerPage={setRowsPerPage}
          loading={loading}
          error={error}
        />
    </>
  );
};

export default Dashboard;
