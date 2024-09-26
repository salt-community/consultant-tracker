import "./dashboard.css";
import FilterField from "../filter/filter";

import {Provider, useDispatch, useSelector} from "react-redux";
import {RootState, store} from "../../store/store";
import DashboardHeader from "./dashboard-header/dashboard-header";
import GanttChart from "../gantt-chart/gantt-chart";
import  {Toaster} from "react-hot-toast";
import {setModalOpen} from "../../store/slices/ModalSlice.ts";
import ModalComponent from "../modal-db/modal-db.tsx";
import Button from "@mui/material/Button";

const Dashboard = () => {
  const role = useSelector((state: RootState) => state.authorization.role)
  const dispatch = useDispatch();

  return (
    <Provider store={store}>
      {role === "ADMIN" && <Button onClick={()=> dispatch(setModalOpen(true))} variant="contained" color="primary">Populate db</Button>}
      <ModalComponent />
      <DashboardHeader/>
      <FilterField/>
      <GanttChart/>
      <Toaster/>
    </Provider>
  );
};

export default Dashboard;