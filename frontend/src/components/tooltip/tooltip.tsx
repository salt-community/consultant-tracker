import Tooltip from '@mui/material/Tooltip';
import {Dispatch, SetStateAction} from "react";
import {ConsultantItemsType} from "@/types";
import './tooltip.css';

type Props = {
  content: ConsultantItemsType
  setOpen: Dispatch<SetStateAction<boolean>>
  open: boolean,
}

const TooltipComponent = ({setOpen, open, content}: Props) => {

  const handleTooltipClose = () => {
    setOpen(false);
    document.addEventListener('click', ()=>{})
  };

  return (
      <Tooltip
        PopperProps={{
          disablePortal: true,
        }}
        onClose={handleTooltipClose}
        open={open}
        disableFocusListener
        disableHoverListener
        disableTouchListener
        title=""
        className={open ? "tooltip__container" : "hidden"}
      >
        <button onClick={handleTooltipClose}>Close</button>
        <p>Consultant name: {content.details.fullName}</p>
        <p>Total remaining days: {content.details.totalDaysStatistics.totalRemainingDays}</p>
        <p>Total vacation days used: {content.details.totalDaysStatistics.totalVacationDaysUsed}</p>
        <p>Total worked days: {content.details.totalDaysStatistics.totalWorkedDays}</p>
        <p>Worked hours: {content.details.totalDaysStatistics.totalWorkedDays * 8}</p>
        <p>Remaining hours: {2024 - content.details.totalDaysStatistics.totalWorkedDays * 8}</p>
        <p>Client: {content.details.client}</p>
      </Tooltip>
  );
};

export default TooltipComponent;