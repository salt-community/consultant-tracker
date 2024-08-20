import Tooltip from "@mui/material/Tooltip";
import { Dispatch, SetStateAction } from "react";
import { ConsultantItemsType } from "@/types";
import "./tooltip-component.css";
import { Weekend } from "@mui/icons-material";
import { differenceInBusinessDays, isWeekend } from "date-fns";
import { Dayjs } from "dayjs";
type Props = {
  content: ConsultantItemsType;
  setOpenTooltip: Dispatch<SetStateAction<boolean>>;
  openTooltip: boolean;
};

const TooltipComponent = ({ setOpenTooltip, openTooltip, content }: Props) => {
  const handleTooltipClose = () => {
    setOpenTooltip(false);
  };

  // const startDate = new Date(content.start_time);
  // const endDate = new Date(content.end_time);

  // let totalDays = differenceInBusinessDays(startDate, endDate);

  // if (!isWeekend(endDate)) {
  //   totalDays = +1;
  // }

  // const week = (start_date: Dayjs, end_date: Dayjs) => {
  //   let totalDays = 0;
  //   let currentDate = start_date.startOf("day");
  //   while (currentDate < end_date) {
  //     const isWeekend = currentDate.day() === 0 || currentDate.day() === 6;
  //     if (!isWeekend) {
  //       totalDays = +1;
  //     }
  //     currentDate.add(1, "day");
  //   }
  //   return totalDays;
  // };

  // let businessDays = week(content.start_time, content.end_time);

  return (
    <Tooltip
      PopperProps={{
        disablePortal: true,
      }}
      onClose={handleTooltipClose}
      open={openTooltip}
      disableFocusListener
      disableHoverListener
      disableTouchListener
      title=""
      className={openTooltip ? "tooltip__container" : "hidden"}
    >
      <div className="tooltip-content">
        <button onClick={handleTooltipClose} className="close-button">
          Close
        </button>
        <div className="item-details">
          <h3>{content.details.name}</h3>
          <p>{content.title}</p>
          {/* <p>Total Days Selected: {businessDays}</p> */}
          <p>Start Date: {content.start_time.format("ddd, DD-MMM-YY")}</p>
          <p>End Date: {content.end_time.format("ddd, DD-MMM-YY")}</p>
        </div>
      </div>
    </Tooltip>
  );
};

export default TooltipComponent;
