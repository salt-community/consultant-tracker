"use client";
import React, { ChangeEvent, useState } from "react";
import "./filter.css";

function FilterField() {
  const [filterValue, setFilterValue] = useState("");
  const [filterBy, setFilterBy] = useState("");

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterValue(e.target.value);
    console.log("filter: ", filterValue);
  };

  const handleCriteriaSelection = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterBy(e.target.value);
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
        />{" "}
        Consultant
        <input
          type="radio"
          value="Client"
          name="filter"
          onChange={(e) => handleCriteriaSelection(e)}
        />{" "}
        Client
      </div>
      <button>Search</button>
      <button>Clear Filter</button>
    </>
  );
}

export default FilterField;
