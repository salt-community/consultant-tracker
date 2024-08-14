"use client";
import {ChangeEvent, Dispatch, SetStateAction, useEffect, useState} from "react";
import "./filter.css";
import { useTableContext } from "@/context/table";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Multiselect from "@/components/filter/multiselect/multiselect";
type Props ={
  lisOfResponsiblePt: string[],
  listOfClients: string[],
  setListOfResponsiblePt: Dispatch<SetStateAction<string[]>>
  setListOfClients: Dispatch<SetStateAction<string[]>>
}
function FilterField({lisOfResponsiblePt, listOfClients, setListOfResponsiblePt, setListOfClients}:Props) {
  const [filterValue, setFilterValue] = useState("");
  const [responsiblePts, setResponsiblePts] = useState(["Josefin Stål"]);
  const [clients, setClients] = useState([]);
  const data = useTableContext();
  // const filterData = () => {

  //   const filteredData = [...data.data.consultants].filter((df) => {
  //     const name = df.fullName.toLowerCase();
  //     const searchValue = filterValue.toLowerCase();
  //     if (responsiblePts.length === 0) {
  //       return name.includes(searchValue);
  //     }
  //     return name.includes(searchValue) && responsiblePts.includes("Josefin Stål");
  //   });
  //   data!.setFilteredData({
  //     pageNumber: 0,
  //     totalPages: 0,
  //     totalConsultants: 0,
  //     consultants: filteredData,
  //   });
  // };

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterValue(e.target.value);
    // filterData();
  };

  const handleClear = () => {
    setFilterValue("");
    setResponsiblePts(["Josefin Stål"]);
    setClients([])
  };

  const handlePtsSelection = (selectionArr: string[]) => {
    setResponsiblePts(selectionArr);
    // filterData();
  };

  const handleClientSelection = (selectionArr: string[]) => {
    // setListOfClients(selectionArr);
    // filterData();
  };

  useEffect(() => {
    // filterData();
  }, [responsiblePts, filterValue]);

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
          fullList={listOfClients}
          handleSelection={handleClientSelection}
          setSelection={setClients}
          selected={clients}
          label="Filter by client"
        />
        <Multiselect
          fullList={lisOfResponsiblePt}
          handleSelection={handlePtsSelection}
          setSelection={setResponsiblePts}
          selected={responsiblePts}
          label="Filter by responsible pt"
        />
        <Button onClick={handleClear} variant="contained">
          Clear filter
        </Button>
      </fieldset>
    </section>
  );
}

export default FilterField;
