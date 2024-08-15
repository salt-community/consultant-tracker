"use client";
import {
  ChangeEvent,
  Dispatch,
  SetStateAction,
  useEffect,
  useState,
} from "react";
import "./filter.css";
import { useTableContext } from "@/context/table";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Multiselect from "@/components/filter/multiselect/multiselect";
type Props = {
  lisOfResponsiblePt: string[];
  listOfClients: string[];
  filterPts: string[];
  filterClients: string[];
  setFilterPts: Dispatch<SetStateAction<string[]>>;
  setFilterClients: Dispatch<SetStateAction<string[]>>;
  setFilterName: Dispatch<SetStateAction<string>>;
  filterName: string;
};
function FilterField({
  lisOfResponsiblePt,
  listOfClients,
  filterPts,
  filterClients,
  setFilterPts,
  setFilterClients,
  setFilterName,
  filterName
}: Props) {

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterName(e.target.value)
  };

  const handleClear = () => {
    setFilterPts(["Josefin Stål"]);
    setFilterClients([]);
    setFilterName("")
  };

  const handlePtsSelection = (selectionArr: string[]) => {
    setFilterPts(selectionArr);
  };

  const handleClientSelection = (selectionArr: string[]) => {
    setFilterClients(selectionArr);
  };

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
            value={filterName}
            onChange={handleInputChange}
          />
        </div>
        <Multiselect
          fullList={listOfClients}
          handleSelection={handleClientSelection}
          setSelection={setFilterClients}
          selected={filterClients}
          label="Filter by client"
        />
        <Multiselect
          fullList={lisOfResponsiblePt}
          handleSelection={handlePtsSelection}
          setSelection={setFilterPts}
          selected={filterPts}
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
