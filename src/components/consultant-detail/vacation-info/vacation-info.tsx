import React from "react";
import { PiPencilSimpleLineThin } from "react-icons/pi";
import "./vacation-info.css";

const VacationInfo = () => {
  return (
    <div className="vacation-info__wrapper">
      <PiPencilSimpleLineThin style={{ alignSelf: "end" }} />
      <p>Available vacation days: 10</p>
      <p>Used vacation days: 2</p>
    </div>
  );
};

export default VacationInfo;
