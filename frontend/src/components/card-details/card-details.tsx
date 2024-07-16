"use client"

import { SyntheticEvent, useState } from "react";
import TabsComponent from "../tabs/tabs"
import Schedule from "../consultant-detail/schedule/schedule";
import VacationInfo from "../consultant-detail/vacation-info/vacation-info";
import Client from "../consultant-detail/client/client";

const CardDetails = () => {
    const [value, setValue] = useState("clients.tsx");

    const handleChange = (event: SyntheticEvent, newValue: string) => {
      setValue(newValue);
    }

    const content = () => {
      switch (value) {
        case "schedule":
          return <Schedule />;
        case "vacation":
          return <VacationInfo />;
        default:
          return <Client />;
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