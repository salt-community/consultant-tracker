"use client";
import {ChangeEvent, useEffect} from "react";
import "./filter.css";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Multiselect from "@/components/filter/multiselect/multiselect";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, RootState} from "@/store/store";
import {
  setDebounceFilterName,
  setFilterClients,
  setFilterName,
  setFilterPts,
  setListOfClients,
  setListOfPts
} from "@/store/slices/FilterFieldSlice";
import {user} from "@/utils/utils";
import {ClientsAndPtsListResponseType} from "@/types";
import {getAllClientsAndPts} from "@/api";
import {setPage} from "@/store/slices/PaginationSlice";


function FilterField() {
  const filterPts = useSelector((state: RootState) => state.filterField.filterPts)
  const filterClients = useSelector((state: RootState) => state.filterField.filterClients)
  const filterName = useSelector((state: RootState) => state.filterField.filterName)
  const listOfClients = useSelector((state: RootState) => state.filterField.listOfClients)
  const listOfPts = useSelector((state: RootState) => state.filterField.listOfPts)

  const dispatch = useDispatch<AppDispatch>();
  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    dispatch(setPage(0));
    dispatch(setFilterName(e.target.value));
  };

  useEffect(() => {
    const delayDebounceFn = setTimeout(() => {
      dispatch(setDebounceFilterName(filterName));
    }, 500);
    return () => clearTimeout(delayDebounceFn);
  }, [filterName, 500]);

  const handlePtsSelection = (selectionArr: string[]) => {
    dispatch(setPage(0));
    dispatch(setFilterPts(selectionArr));
  };

  const handleClientSelection = (selectionArr: string[]) => {
    dispatch(setPage(0));
    dispatch(setFilterClients(selectionArr));
  };

  const handleClear = () => {
    dispatch(setFilterPts([user]));
    dispatch(setFilterClients([]));
    dispatch(setFilterName(""))
  };

  useEffect(() => {
    getAllClientsAndPts()
      .then((res: ClientsAndPtsListResponseType) => {
        dispatch(setListOfPts(res.pts));
        dispatch(setListOfClients(res.clients));
      });
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
            value={filterName}
            onChange={handleInputChange}
          />
        </div>
        <Multiselect
          fullList={listOfClients}
          handleSelection={handleClientSelection}
          setSelection={(clients)=>dispatch(setFilterClients(clients))}
          selected={filterClients}
          label="Filter by client"
        />
        <Multiselect
          fullList={listOfPts}
          handleSelection={handlePtsSelection}
          setSelection={(pts)=>dispatch(setFilterPts(pts))}
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