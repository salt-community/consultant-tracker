import TextField from "@/components/text-field/text-field";
import { Box } from "@mui/material";
import React, { ChangeEvent, FormEvent, useState } from "react";
import "./salt-contract.css";
import { usePathname } from "next/navigation";
import { consultantDetailsData } from "@/mockData";
import Edit from "@/components/edit/edit";
import { useDetailsContext } from "@/context/details";
import { ConsultantDetailsDataType } from "@/types";

type Props = {
  startDate: string;
  remainingHours: number;
};

const SaltContract = ({ startDate, remainingHours }: Props) => {
  const [saltContractData, setSaltContractData] = useState({
    startDate,
    remainingHours,
  });
  const [readonly, setReadonly] = useState(true);
  const idParam = usePathname().split("/").pop();
  const details = useDetailsContext();

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    details.setData(
      details.data.map((el: ConsultantDetailsDataType) => {
        if (el.id === idParam) {
          el.startDate = saltContractData.startDate;
          el.remainingHours = saltContractData.remainingHours;
        }
        return el;
      })
    );
    setReadonly(true);

    consultantDetailsData.forEach((el) => {
      if (el.id === idParam) {
        el.startDate = saltContractData.startDate;
        el.remainingHours = saltContractData.remainingHours;
      }
    });
  };
  const filterData = () => {
    return details.data.filter((el) => el.id === idParam)[0];
  };

  const handleClick = (v: boolean) => {
    setReadonly(v);
    if (v) {
      const filteredData = filterData();
      const data = {
        startDate: filteredData.startDate,
        remainingHours: filteredData.remainingHours,
      };
      setSaltContractData(data);
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setSaltContractData({
      ...saltContractData,
      [name]: value,
    });
  };

  return (
    <div className="salt-contract-card">
      <Box
        component="form"
        className="salt-contract-form"
        noValidate
        autoComplete="off"
        onSubmit={(e) => handleSubmit(e)}
      >
        <div className="edit-card__container">
          <h2>Salt Contract</h2>
          <Edit readonly={readonly} handleClick={handleClick} />
        </div>
        <TextField
          label="Start date"
          onChange={handleChange}
          value={saltContractData.startDate}
          readonly={readonly}
          name="startDate"
        />
        <TextField
          label="Remaining hours"
          onChange={handleChange}
          value={saltContractData.remainingHours}
          readonly={readonly}
          name="remainingHours"
        />
      </Box>
    </div>
  );
};

export default SaltContract;
