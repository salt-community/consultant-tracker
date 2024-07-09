import EditCard from "@/components/edit-card/edit-card";
import React, { useState } from "react";
import "./salt-contract.css";
import SaltContractForm from "./form/form";

type Props = {
  startDate: string;
  remainingHours: number;
};

const SaltContract = ({ startDate, remainingHours }: Props) => {
  const [readonly, setReadonly] = useState(true);

  const handleClick = (v: boolean) => {
    setReadonly(v);
  };

  return (
    <div className="salt-contract-card">
      <EditCard
        title="Salt Contract"
        readonly={readonly}
        handleClick={handleClick}
      />
      <SaltContractForm startDate={startDate} readonly={readonly} remainingHours={remainingHours} />
    </div>
  );
};

export default SaltContract;
