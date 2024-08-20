import Tooltip from "@mui/material/Tooltip";
import { Dispatch, SetStateAction } from "react";
import { ConsultantItemsType } from "@/types";
import "./tooltip-component.css";

type Props = {
  content: ConsultantItemsType;
  setOpenTooltip: Dispatch<SetStateAction<boolean>>;
  openTooltip: boolean;
};

const TooltipComponent = ({ setOpenTooltip, openTooltip, content }: Props) => {
  console.log("Selected Item Content:", content);
  console.log("Tooltip Open:", openTooltip);

  const handleTooltipClose = () => {
    setOpenTooltip(false);
  };

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
          <p>Title: {content.title}</p>
          <p>
            Total Days Selected:{" "}
            {content.end_time.diff(content.start_time, "day") + 1}
          </p>
          <p>Start Date: {content.start_time.format("ddd, DD-MMM-YY")}</p>
          <p>End Date: {content.end_time.format("ddd, DD-MMM-YY")}</p>
          <p>Project Name: {content.details.projectName}</p>
          <p>Total Worked Days: {content.details.totalWorkedDays}</p>
          <p>Remaining Days: {content.details.totalRemainingDays}</p>
          <p>
            Total Vacation Days Used: {content.details.totalVacationDaysUsed}
          </p>
        </div>
      </div>
    </Tooltip>
  );
};

export default TooltipComponent;
