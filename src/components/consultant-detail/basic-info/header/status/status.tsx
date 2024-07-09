import React from "react";
import Indicator from "@/components/table/table-legend/indicator/indicator";
import "./status.css";

type Props = {
  status: string;
};
const HeaderStatus = ({ status }: Props) => {
  return (
    <h5 className="basic-info__indicator">
      <Indicator value={status} /> {status}
    </h5>
  );
};

export default HeaderStatus;
