import "./dashboard.css";
import { FilterField, GanttChart, ModalComponent } from "../../components";
import { Provider, useDispatch, useSelector } from "react-redux";
import { RootState, store } from "../../store/store";
import DashboardHeader from "./dashboard-header/dashboard-header";
import { Toaster } from "react-hot-toast";
import { setModalOpen } from "../../store/slices/ModalSlice.ts";
import Button from "@mui/material/Button";
import RefreshIcon from "@mui/icons-material/Refresh";
import Hero from "../hero/hero.tsx";

export const Dashboard = () => {
  const role = useSelector((state: RootState) => state.authorization.role);
  const dispatch = useDispatch();

  return (
    <Provider store={store}>
      <Hero/>
      {role === "ADMIN" && (
        <Button
          onClick={() => dispatch(setModalOpen(true))}
          variant="contained"
          color="primary"
          className="button-wrapper"
          style={{
            backgroundColor: "#D3D3D3",
            color: "black",
            fontWeight: 300,
          }}
        >
          Refresh database <RefreshIcon style={{height: "16px"}} />
        </Button>
      )}
      <ModalComponent />
      {/* <DashboardHeader /> */}
      <FilterField />
      <GanttChart />
      <Toaster />
    </Provider>
  );
};
