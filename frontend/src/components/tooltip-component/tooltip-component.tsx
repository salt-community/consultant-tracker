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
  const handleTooltipClose = () => {
    setOpenTooltip(false);
  };

  

  // let totalWorkDays = workingDays(content.start_time, content.end_time);

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
          <p>Total Days Selected: {totalWorkDays}</p>
          <p>Start Date: {content.start_time.format("ddd, DD-MMM-YY")}</p>
          <p>End Date: {content.end_time.format("ddd, DD-MMM-YY")}</p>
        </div>
      </div>
    </Tooltip>
  );
};

export default TooltipComponent;
