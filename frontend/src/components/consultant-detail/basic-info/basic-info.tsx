"use client";

import { ConsultantFetchType} from "@/types";
import "./basic-info.css";
import { useEffect, useState } from "react";
import BasicInfoHeader from "@/components/consultant-detail/basic-info/header/header";
import { getConsultantById } from "@/api";
import CardDetails from "@/components/card-details/card-details";
import TimeItemDetails from "@/components/time-item-details/time-item-details";
import {useSelector} from "react-redux";
import {RootState} from "@/store/store";


const BasicInfo = () => {
  const [personalData, setPersonalData] = useState<ConsultantFetchType>();
  const open = useSelector((state: RootState) => state.ganttChart.open)
  const fetchConsultantById = () => {

    if (id) getConsultantById(id).then((res) => {
      console.log(res);
      setPersonalData(res)});
  };
  const id = useSelector((state: RootState) => state.ganttChart.id)
  useEffect(() => {
    fetchConsultantById();
  }, [id]);

  return (
    personalData && (
      <div className={open ? "basic-info__wrapper show" : "hide"}>
        <BasicInfoHeader name={personalData.fullName} />
        <div className="basic-info__data">
          <CardDetails
            personalData={personalData}
          />
        </div>
        <TimeItemDetails/>
      </div>
    )
  );
};

export default BasicInfo;
