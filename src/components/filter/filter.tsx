"use client";
import React, { ChangeEvent, useState } from "react";
import "./filter.css";
import { ConsultantDataType } from "@/types";
import { consultantsData, statusOptions } from "@/mockData";

function FilterField() {
  const [filterValue, setFilterValue] = useState("");
  const [filterBy, setFilterBy] = useState("Consultant");
  const [data, setData] = useState<ConsultantDataType[]>(consultantsData);
  const [status, setStatus] = useState("show all");
  const [filteredDataState, setFilteredDataState] = useState<
    ConsultantDataType[]
  >([]);

  const checkFilterValue = (dataVal: string, filterVal: string) => {
    return dataVal.toLowerCase().includes(filterVal.toLowerCase());
  };

  const checkStatus = (dataVal: string, statusVal: string) => {
    if (statusVal === "show all") {
      console.log("checkStatus dataVal: ", dataVal);
      console.log("checkStatus status: ", statusVal);
      return true;
    }
    console.log("checkStatus dataVal: ", dataVal);
    console.log("checkStatus status: ", statusVal);
    return dataVal === statusVal;
  };

  const filterData = (searchParam: string, selectedStatus: string) => {
    const filteredData = [...data].filter((df) => {
      if (filterBy === "Consultant") {
        return (
          checkFilterValue(df.name, searchParam) &&
          checkStatus(df.status, selectedStatus)
        );
      }
      return (
        checkFilterValue(df.client, searchParam) &&
        checkStatus(df.status, selectedStatus)
      );
    });
    setFilteredDataState(filteredData);
    console.log("filteredData: ", filteredData);
  };

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterValue(e.target.value);
    filterData(e.target.value, status);
  };

  const handleCriteriaSelection = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterBy(e.target.value);
    handleClear();
  };

  const handleClear = () => {
    setFilterValue("");
    setStatus("show all");
  };

  const handleStatusChange = (e: ChangeEvent<HTMLSelectElement>) => {
    setStatus(e.target.value);
    filterData(filterValue, e.target.value);
  };

  return (
    <>
      <input
        type="text"
        value={filterValue}
        onChange={(e) => handleInputChange(e)}
        placeholder="Type to filter by ..."
      />
      <div>
        <input
          type="radio"
          value="Consultant"
          name="filter"
          onChange={(e) => handleCriteriaSelection(e)}
          checked={filterBy === "Consultant"}
        />
        Consultant
        <input
          type="radio"
          value="Client"
          name="filter"
          onChange={(e) => handleCriteriaSelection(e)}
          checked={filterBy === "Client"}
        />
        Client
      </div>
      <div>
        <label htmlFor="status">Consultant status</label>
        <select
          name="status"
          id="status"
          onChange={(e) => handleStatusChange(e)}
        >
          {statusOptions.map((st, index) => (
            <option value={st.toLowerCase()} key={index}>
              {st}
            </option>
          ))}
        </select>
      </div>
      {/* <button>Search</button> */}
      <button onClick={handleClear}>Clear Filter</button>
    </>
  );
}

export default FilterField;
