"use client"
import {useTableContext} from "@/context/table";
import './view-switch.css'
import {Dispatch, SetStateAction} from "react";
import {Button} from "@mui/material";

type Props = {
  setView: Dispatch<SetStateAction<string>>;
  view: string
}
const ViewSwitch = ({setView, view}: Props) => {
  const tableData = useTableContext().data;
  return (
    <div className="view-switch">
      <div>
        {view === "table" &&
            <Button color="primary" variant="contained" onClick={() => setView("timeline")}>Timeline view</Button>}
        {view === "timeline" &&
            <Button color="primary" variant="contained" onClick={() => setView("table")}>Table view</Button>}
      </div>
      <p className="total-results">Total results: {tableData.length}</p>
    </div>
  );
};

export default ViewSwitch;