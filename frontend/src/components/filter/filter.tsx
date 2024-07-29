"use client";
import { ChangeEvent, useState } from "react";
import "./filter.css";
import { consultantsData } from "@/mockData";
import { useTableContext } from "@/context/table";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";

function FilterField() {
  const [filterValue, setFilterValue] = useState("");
  const [responsiblePt, setResponsiblePt] = useState("")
  const data = useTableContext();

  const filterData = (value: string) => {
    const filteredData = [...consultantsData].filter((df) => {
      return df.name.toLowerCase().includes(value.toLowerCase());
    });
    data!.setData(filteredData);
  };

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterValue(e.target.value);
    filterData(e.target.value);
  };

  const handleClear = () => {
    setFilterValue("");
    filterData("");
  };

  return (
    <section>
      <fieldset className="filter-fieldset">
        <legend className="filter-section__title"> Filter</legend>
        <div className="filter-by__wrapper">
          <TextField
            id="outlined-basic"
            label={`By consultant name`}
            variant="outlined"
            className="filter-text__input"
            value={filterValue}
            onChange={handleInputChange}
          />
        </div>
        <Button onClick={handleClear} variant="contained">
          Clear filter
        </Button>
      </fieldset>
    </section>
  );
}

export default FilterField;
