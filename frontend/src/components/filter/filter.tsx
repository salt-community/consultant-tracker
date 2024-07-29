"use client";
import {ChangeEvent, useEffect, useState} from "react";
import "./filter.css";
import {consultantsData, allPts} from "@/mockData";
import {useTableContext} from "@/context/table";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Multiselect from "@/components/filter/multiselect/multiselect";

function FilterField() {
  const [filterValue, setFilterValue] = useState("");
  const [responsiblePt, setResponsiblePt] = useState(["Josefin Stål"])
  const data = useTableContext();
  const filterData = (value: string | string[]) => {
    const filteredData = [...consultantsData].filter((df) => {
      const name =  df.name.toLowerCase();
      const searchValue = filterValue.toLowerCase();
      switch (typeof value) {
        case "string": {
          const valueLowerCase = value.toLowerCase()
          return responsiblePt.length === 0
            ? name.includes(valueLowerCase)
            : name.includes(valueLowerCase) && responsiblePt.includes(df.pt)
        }
        default: {
          return value.length !== 0
            ? name.includes(searchValue) && value.includes(df.pt)
            : name.includes(searchValue)
        }
      }
    });
    console.log('filteredData',filteredData)
    data!.setData(filteredData);
  };


  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterValue(e.target.value);
    filterData(e.target.value);
  };

  const handleClear = () => {
   setFilterValue("");
   setResponsiblePt(["Josefin Stål"]);
   filterData("");
  };

  const handlePtsSelection = (selectionArr: string[]) => {
    setResponsiblePt(selectionArr);
    filterData(selectionArr)
  }

  useEffect(() => {
    filterData("");
  }, []);

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
        <Multiselect allPts={allPts}
                     handlePtsSelection={handlePtsSelection}
                     setResponsiblePt={setResponsiblePt}
                     responsiblePt={responsiblePt}/>
        <Button onClick={handleClear} variant="contained">
          Clear filter
        </Button>
      </fieldset>
    </section>
  );
}

export default FilterField;
