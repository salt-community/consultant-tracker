"use client";
import {ChangeEvent, useState} from "react";
import "./filter.css";
import {consultantsData} from "@/mockData";
import {useTableContext} from "@/context/context";
import SelectStatus from "@/components/filter/select-status/select-status";
import {SelectChangeEvent} from "@mui/material/Select";
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import FilterBy from "@/components/filter/filter-by/filter-by";

function FilterField() {
  const [filterValue, setFilterValue] = useState("");
  const [filterBy, setFilterBy] = useState("Consultant");
  const [status, setStatus] = useState("show all");
  const data = useTableContext();
  const handleStatusChange = (e: SelectChangeEvent) => {
    setStatus(e.target.value as string);
    filterData(filterValue, e.target.value);
  }

  const checkFilterValue = (dataVal: string, filterVal: string) => {
    return dataVal.toLowerCase().includes(filterVal.toLowerCase());
  };

  const checkStatus = (dataVal: string, statusVal: string) => {
    if (statusVal === "show all") {
      return true;
    }
    return dataVal === statusVal;
  };

  const filterData = (searchParam: string, selectedStatus: string) => {
    const filteredData = [...consultantsData].filter((df) => {
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
    data!.setData(filteredData);
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
    filterData("", "show all");
  };

  return (
    <section>
      <fieldset className="filter-fieldset">
        <legend className="filter-section__title"> Filter</legend>
        <div className="filter-by__wrapper">
          <TextField id="outlined-basic"
                     label={`Type to filter by ${filterBy.toLowerCase()}`}
                     variant="outlined"
                     className="filter-text__input"
                     value={filterValue}
                     onChange={handleInputChange}/>
          <FilterBy value={filterBy} handleChange={handleCriteriaSelection}/>
        </div>
        <SelectStatus status={status} handleChange={handleStatusChange}/>
        <Button onClick={handleClear} variant="contained">Clear filter</Button>
      </fieldset>
    </section>
  );
}

export default FilterField;
