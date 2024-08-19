"use client";

import { ConsultantFetchType } from "@/types";
import "./basic-info.css";
import { useEffect, useState } from "react";
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
};

const BasicInfo = ({ id }: Props) => {
  const [personalData, setPersonalData] = useState<ConsultantFetchType>();
  const fetchConsultantById = () => {
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
          <CardDetails personalData={personalData}/>
        </div>
      </div>
    )
  );
};

export default BasicInfo;
