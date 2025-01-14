import {ChangeEvent, useEffect} from "react";
import {TextInput} from "../text-input"
import "./filter.css";
import Button from "@mui/material/Button";
import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
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
import {setPage} from "../../store/slices/PaginationSlice";
import {AppDispatch, RootState} from "../../store/store";
import Multiselect from "./multiselect/multiselect";
import {getAllClientsAndPts} from "../../api";
import {ClientsAndPtsListResponseType} from "../../types";
import {useAuth} from "@clerk/clerk-react";
import {template} from "../../constants";

export const FilterField = () => {
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
          <TextInput
            label={`Consultant name`}
            name="consultant-name-input"
            value={filterName}
            onChange={handleInputChange}
          />
        </div>
        <Multiselect
          fullList={listOfClients}
          handleSelection={handleClientSelection}
          setSelection={(clients) => dispatch(setFilterClients(clients))}
          selected={filterClients}
          label="Client"
        />
        <Multiselect
          fullList={listOfPts}
          handleSelection={handlePtsSelection}
          setSelection={(pts) => dispatch(setFilterPts(pts))}
          selected={filterPts}
          label="Responsible P&T"
        />
        <Button onClick={handleClear} variant="contained">
          Clear filter
        </Button>
        <div className="filter-by__consultants">
          <FormControlLabel
            control={<Checkbox 
              sx={{
                color: 'black',
                '&.Mui-checked': {
                  color: 'black',
                },
              }}
            />}
            label="Include PGPs"
            checked={includePgps}
            onChange={handlePgpSelection}
          />
        </div>
      </fieldset>
    </section>
  );
}

