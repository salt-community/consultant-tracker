import React from "react";

type Props = {
  value: string;
};

const Indicator = ({ value }: Props) => {
  return <span className={`status-indicator ${value}`}></span>;
};

export default Indicator;
