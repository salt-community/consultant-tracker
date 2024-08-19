"use client"

import { SyntheticEvent, useState } from "react";
import TabsComponent from "../tabs/tabs"
import Schedule from "../consultant-detail/schedule/schedule";
import VacationInfo from "../consultant-detail/vacation-info/vacation-info";
import Client from "../consultant-detail/client/client";
import PersonalData from "../consultant-detail/basic-info/personal-data/personal-data";
import { ConsultantFetchType } from "@/types";

type Props = {
  personalData: ConsultantFetchType;
};

const CardDetails = ({personalData}: Props) => {
    const [value, setValue] = useState("personalData");
    const { email, phoneNumber, client, totalDaysStatistics,registeredTimeDtoList } = personalData;

    const handleChange = (event: SyntheticEvent, newValue: string) => {
      setValue(newValue);
    }

  const content = () => {
    switch (value) {
      case "schedule":
        return <Schedule />;
      case "vacation":
        return <VacationInfo />;
        case "clients":
          return <Client registeredTime={registeredTimeDtoList}/>;
      default:
        return <PersonalData email={email} phone={phoneNumber} client={client} totalDaysStatistics={totalDaysStatistics}/>;
    }
  };

  return (
    <div className="detail-page__tabs-wrapper">
    <TabsComponent value={value} handleChange={handleChange} />
    <div className="consultant-detail__card">{content()}</div>
  </div>
  )
}

export default CardDetails