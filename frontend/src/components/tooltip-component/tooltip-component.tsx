import Tooltip from "@mui/material/Tooltip";
import { Dispatch, SetStateAction } from "react";
import { ConsultantFetchType, ConsultantItemsType } from "@/types";
import "./tooltip-component.css";


type Props = {
  content: ConsultantFetchType;
  setOpenTooltip: Dispatch<SetStateAction<boolean>>;
  openTooltip: boolean;
};

const TooltipComponent = ({ setOpenTooltip, openTooltip, content }: Props) => {
  console.log("content",content);

    console.log("openTooltip",openTooltip);
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
      <div>
        <button onClick={handleTooltipClose}>Close</button>
        <p>Consultant name: {content.fullName}</p>
        {content.registeredTimeDtoList.map((el, key) => {
            return (
                <div key={key}>
                    {/* <p>Client: {el.projectName}</p> */}
                    <p>Total days selected: {el.days}</p>
                </div>
                );
            }
            )
        }
        <p>Client: {content.client}</p>
      </div>
    </Tooltip>
  );
};

export default TooltipComponent;
