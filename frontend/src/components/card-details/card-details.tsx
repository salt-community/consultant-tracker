"use client";

import {SyntheticEvent, useState } from "react";
import TabsComponent from "../tabs/tabs";
import Schedule from "../consultant-detail/schedule/schedule";
import AbsenceInfo from "@/components/consultant-detail/absence-info/absence-info";
import Client from "../consultant-detail/client/client";
import PersonalData from "../consultant-detail/basic-info/personal-data/personal-data";
import {useSelector} from "react-redux";
import {RootState} from "@/store/store";


const CardDetails = () => {
  const [value, setValue] = useState("personalData");
  const personalData = useSelector((state:RootState)=>state.basicInfo.personalData)
  const handleChange = (event: SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };

  const content = () => {
    switch (value) {
      case "schedule":
        return <Schedule />;
      case "clients":
        return <Client />;
      case "absences":
        return (
          <AbsenceInfo />
        );
      default:
        return (
          <PersonalData/>
        );
    }
  };

  return (
    <div className="detail-page__tabs-wrapper">
      <TabsComponent value={value} handleChange={handleChange} />
      <div className="consultant-detail__card">{content()}</div>
    </div>
  );
};

export default CardDetails;
