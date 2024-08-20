"use client";

import { ConsultantFetchType, ConsultantItemsType } from "@/types";
import "./basic-info.css";
import { Dispatch, SetStateAction, useEffect, useState } from "react";
import BasicInfoHeader from "@/components/consultant-detail/basic-info/header/header";
import { getConsultantById } from "@/api";
import ContactDetails from "./contact-details/contact-details";
import PersonalData from "./personal-data/personal-data";
import { Card } from "@mui/material";
import CardDetails from "@/components/card-details/card-details";
import { Schedule } from "@mui/icons-material";
import VacationInfo from "../vacation-info/vacation-info";
import Client from "../client/client";

type Props = {
  id: string;
  modalData: ConsultantItemsType;
  openTooltip: boolean;
  setOpenTooltip: Dispatch<SetStateAction<boolean>> ;
};

const BasicInfo = ({ id, modalData, openTooltip, setOpenTooltip}: Props) => {
  const [personalData, setPersonalData] = useState<ConsultantFetchType>();
  const fetchConsultantById = () => {
    console.log("id",id);
    if (id) getConsultantById(id).then((res) => setPersonalData(res));
  };

  useEffect(() => {
    fetchConsultantById();
  }, [id]);

  return (
    personalData && (
      <div className="basic-info__wrapper">
        <BasicInfoHeader name={personalData.fullName} />
        <div className="basic-info__data">
          <CardDetails personalData={personalData} modalData={modalData} openTooltip={openTooltip} setOpenTooltip={setOpenTooltip}/>
        </div>
      </div>
    )
  );
};

export default BasicInfo;
