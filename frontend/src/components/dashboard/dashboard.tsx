import "./dashboard.css";
import {FilterField, GanttChart, ModalComponent} from "../../components";
import {Provider, useDispatch, useSelector} from "react-redux";
import {RootState, store} from "../../store/store";
import DashboardHeader from "./dashboard-header/dashboard-header";
import {Toaster} from "react-hot-toast";
import {setModalOpen} from "../../store/slices/ModalSlice.ts";
import Button from "@mui/material/Button";

export const Dashboard = () => {
  const role = useSelector((state: RootState) => state.authorization.role)
  const dispatch = useDispatch();

  return (
    <Provider store={store}>
      {role === "ADMIN" &&
          <Button onClick={() => dispatch(setModalOpen(true))} variant="contained" color="primary" className="button-wrapper" style={{ backgroundColor: '#D3D3D3', color: 'black', fontWeight: 300,  }}>
              Refresh database hi
          </Button>
      }
      <ModalComponent/>
      <DashboardHeader/>
      <FilterField/>
      <GanttChart/>
      <Toaster/>
    </Provider>
  );
};
