import {SyntheticEvent, useState } from "react";
import TabsComponent from "../tabs/tabs";
import Schedule from "../consultant-detail/schedule/schedule";
import Client from "../consultant-detail/client/client";
import PersonalData from "../consultant-detail/basic-info/personal-data/personal-data";
import AbsenceInfo from "../consultant-detail/absence-info/absence-info";


const CardDetails = () => {
  const [value, setValue] = useState("personalData");
  const handleChange = (_event: SyntheticEvent, newValue: string) => {
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
