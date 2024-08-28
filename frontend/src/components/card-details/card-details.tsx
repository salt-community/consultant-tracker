"use client";

import { Dispatch, SetStateAction, SyntheticEvent, useState } from "react";
import TabsComponent from "../tabs/tabs";
import Schedule from "../consultant-detail/schedule/schedule";
import AbsenceInfo from "@/components/consultant-detail/absence-info/absence-info";
import Client from "../consultant-detail/client/client";
import PersonalData from "../consultant-detail/basic-info/personal-data/personal-data";
import { ConsultantFetchType, ConsultantItemsType } from "@/types";

type Props = {
  personalData: ConsultantFetchType;
  modalData: ConsultantItemsType;
  openTooltip: boolean;
  setOpenTooltip: Dispatch<SetStateAction<boolean>>;
};

const CardDetails = ({
  personalData,
  modalData,
  openTooltip,
  setOpenTooltip,
}: Props) => {
  const [value, setValue] = useState("personalData");
  const {
    email,
    phoneNumber,
    client,
    totalDaysStatistics,
    registeredTimeDtoList,
  } = personalData;

  const handleChange = (event: SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };

  const content = () => {
    switch (value) {
      case "schedule":
        return <Schedule meetings={personalData.meetings} />;
      case "clients":
        return <Client clientList={personalData.clientsList}/>;
      case "absences":
        return (
          <AbsenceInfo
            totalDaysStatistics={totalDaysStatistics}
          />
        );
      default:
        return (
          <PersonalData
            email={email}
            phone={phoneNumber}
            client={client}
            totalDaysStatistics={totalDaysStatistics}
          />
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
