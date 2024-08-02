"use client";
import { ChangeEvent, useEffect, useState } from "react";
import "./filter.css";
import { consultantsData, allPts } from "@/mockData";
import { useTableContext } from "@/context/table";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Multiselect from "@/components/filter/multiselect/multiselect";

function FilterField() {
  const [filterValue, setFilterValue] = useState("");
  const [responsiblePt, setResponsiblePt] = useState(["Josefin Stål"]);
  const data = useTableContext();
  const filterData = () => {
    const filteredData = [...data.data.consultants].filter((df) => {
      const name = df.fullName.toLowerCase();
      const searchValue = filterValue.toLowerCase();
      if (responsiblePt.length === 0) {
        return name.includes(searchValue);
      }
      return name.includes(searchValue) && responsiblePt.includes("Josefin Stål");
    });
    data!.setFilteredData({
      pageNumber: 0,
      totalPages: 0,
      totalConsultants: 0,
      consultants: filteredData,
    });
  };

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterValue(e.target.value);
    filterData();
  };

  const handleClear = () => {
    setFilterValue("");
    setResponsiblePt(["Josefin Stål"]);
    filterData();
  };

  const handlePtsSelection = (selectionArr: string[]) => {
    setResponsiblePt(selectionArr);
    filterData();
  };

  useEffect(() => {
    filterData();
  }, [responsiblePt, filterValue]);

  return (
    <section className="filter-section">
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
        <Multiselect
          allPts={allPts}
          handlePtsSelection={handlePtsSelection}
          setResponsiblePt={setResponsiblePt}
          responsiblePt={responsiblePt}
        />
        <Button onClick={handleClear} variant="contained">
          Clear filter
        </Button>
      </fieldset>
    </section>
  );
}

export default FilterField;
