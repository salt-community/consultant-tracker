"use client";
import React, {ChangeEvent, useState} from "react";
import "./filter.css";
import {ConsultantDataType} from "@/types";
import {consultantsData, statusOptions} from "@/mockData";

function FilterField() {
  const [filterValue, setFilterValue] = useState("");
  const [filterBy, setFilterBy] = useState("Consultant");
  const [data] = useState<ConsultantDataType[]>(consultantsData);
  const [status, setStatus] = useState("show all");

  const checkFilterValue = (dataVal: string, filterVal: string) => {
    return dataVal.toLowerCase().includes(filterVal.toLowerCase());
  };

  const checkStatus = (dataVal: string, statusVal: string) => {
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
    console.log(filteredData)
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
    <section className="filter-section">
      <h3> Filter</h3>
      <div className="filter-by__wrapper">
        <div>
          <input
            type="radio"
            id="consultant"
            value="Consultant"
            name="filter"
            onChange={(e) => handleCriteriaSelection(e)}
            checked={filterBy === "Consultant"}
          />
          <label htmlFor="consultant"> Consultant</label>
        </div>
        <div>
          <input
            type="radio"
            value="Client"
            id="client"
            name="filter"
            onChange={(e) => handleCriteriaSelection(e)}
            checked={filterBy === "Client"}
          />
          <label htmlFor="client"> Client</label>
        </div>
        <input
          type="text"
          className="filter-text__input"
          value={filterValue}
          onChange={(e) => handleInputChange(e)}
          placeholder={`Type to filter by ${filterBy.toLowerCase()}`}
        />
      </div>
      <div className="filter-status__wrapper">
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
      <button onClick={handleClear} className="clear-filter__button">Clear Filter</button>
    </section>
  );
}

export default FilterField;
