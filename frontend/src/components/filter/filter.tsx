import {ChangeEvent, useEffect} from "react";
import "./filter.css";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import {useDispatch, useSelector} from "react-redux";
import {
  setDebounceFilterName,
  setFilterClients,
  setFilterName,
  setFilterPts,
  setIncludePgps,
  setListOfClients,
  setListOfPts,
} from "../../store/slices/FilterFieldSlice";
import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import {AppDispatch, RootState} from "../../store/store";
import Multiselect from "./multiselect/multiselect";
import {setPage} from "../../store/slices/PaginationSlice";
import {getAllClientsAndPts} from "../../api";
import {ClientsAndPtsListResponseType} from "../../types";
import {useAuth} from "@clerk/clerk-react";
import {template} from "../../constants";

function FilterField() {
  const filterPts = useSelector(
    (state: RootState) => state.filterField.filterPts
  );
  const filterClients = useSelector(
    (state: RootState) => state.filterField.filterClients
  );
  const filterName = useSelector(
    (state: RootState) => state.filterField.filterName
  );
  const listOfClients = useSelector(
    (state: RootState) => state.filterField.listOfClients
  );
  const listOfPts = useSelector(
    (state: RootState) => state.filterField.listOfPts
  );
  const includePgps = useSelector(
    (state: RootState) => state.filterField.includePgps
  );

  const {getToken, signOut} = useAuth();

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
    dispatch(setFilterPts([]));
    dispatch(setFilterClients([]));
    dispatch(setFilterName(""));
    dispatch(setIncludePgps(false));
  };

  const handlePgpSelection = () => {
    if (includePgps && filterClients.includes("PGP")) {
      dispatch(setFilterClients([]));
    }
    dispatch(setIncludePgps(!includePgps));
  };
  const fetchAllClientsAndPts = (token: string) => {
    getAllClientsAndPts(includePgps, token).then(
      (res: ClientsAndPtsListResponseType) => {
        dispatch(setListOfPts(res.pts));
        dispatch(setListOfClients(res.clients));
      }
    );
  }
  const getAccessToken = async () => {
    let token: string | null = "";
    token = await getToken({template});
    if (!token) {
      await signOut();
      return;
    }
    fetchAllClientsAndPts(token);
  };

  useEffect(() => {
    void getAccessToken();
  }, [includePgps]);

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
          setSelection={(clients) => dispatch(setFilterClients(clients))}
          selected={filterClients}
          label="Filter by client"
        />
        <Multiselect
          fullList={listOfPts}
          handleSelection={handlePtsSelection}
          setSelection={(pts) => dispatch(setFilterPts(pts))}
          selected={filterPts}
          label="Filter by responsible pt"
        />
        <Button onClick={handleClear} variant="contained">
          Clear filter
        </Button>
        <div className="filter-by__consultants">
          <FormControlLabel
            control={<Checkbox/>}
            label="Include PGPs"
            checked={includePgps}
            onChange={handlePgpSelection}
          />
        </div>
      </fieldset>
    </section>
  );
}

export default FilterField;
