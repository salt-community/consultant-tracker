"use client";
import React, { ChangeEvent, useState } from "react";
import "./filter.css";
import { ConsultantDataType } from "@/types";
import { consultantsData } from "@/mockData";

function FilterField() {
  const [filterValue, setFilterValue] = useState("");
  const [filterBy, setFilterBy] = useState("Consultant");
  const [data, setData] = useState<ConsultantDataType[]>(consultantsData);

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterValue(e.target.value);
    const filteredData = [...data].filter((df) => {
      if (filterBy === "Consultant") {
        return df.name.toLowerCase().includes(e.target.value.toLowerCase());
      }
      return df.client.toLowerCase().includes(e.target.value.toLowerCase());
    });
  };

  const handleSelection = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterBy(e.target.value);
  };

  const handleClear = () => {
    setFilterValue("");
  }

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
          onChange={(e) => handleSelection(e)}
          checked={filterBy === "Consultant"}
        />
        Consultant
        <input
          type="radio"
          value="Client"
          name="filter"
          onChange={(e) => handleSelection(e)}
          checked={filterBy === "Client"}
        />
        Client
      </div>
      {/* <button>Search</button> */}
      <button onClick={handleClear}>Clear Filter</button>
    </>
  );
}

export default FilterField;
