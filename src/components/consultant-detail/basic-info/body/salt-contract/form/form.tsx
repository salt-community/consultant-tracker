import TextField from "@/components/text-field/text-field";
import { Box } from "@mui/material";
import React, { ChangeEvent, useState } from "react";
import "./form.css";
import { usePathname, useSearchParams } from "next/navigation";
import { consultantDetailsData } from "@/mockData";

type Props = {
  startDate: string;
  remainingHours: number;
  readonly: boolean;
};

const SaltContractForm = ({ startDate, remainingHours, readonly }: Props) => {
  const [saltContractData, setSaltContractData] = useState({
    startDate1: startDate,
    remainingHours1: remainingHours,
  });
  const id = usePathname().split("/").pop();

  const handleSubmit = () => {
    consultantDetailsData.forEach((el) => {
      if (el.id === id) {
        el.startDate = saltContractData.startDate1;
        el.remainingHours = saltContractData.remainingHours1;
      }
    });
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    console.log("value", value);
    setSaltContractData({
      ...saltContractData,
      [name]: value,
    });
  };

  return (
    <Box
      component="form"
      className="salt-contract-form"
      noValidate
      autoComplete="off"
      onSubmit={handleSubmit}
    >
      <TextField
        label="Start date"
        onChange={handleChange}
        value={saltContractData.startDate1}
        readonly={readonly}
      />
      <TextField
        label="Remaining hours"
        onChange={handleChange}
        value={saltContractData.remainingHours1}
        readonly={readonly}
      />
    </Box>
  );
};

export default SaltContractForm;
