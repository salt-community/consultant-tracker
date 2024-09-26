import {SyntheticEvent, useState} from "react";
import {TabsComponent} from "./tabs";
import {AbsenceInfo, Schedule, Client, PersonalData} from "../consultant-detail";

export const CardDetails = () => {
  const [value, setValue] = useState("personalData");
  const handleChange = (_event: SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };

  const content = () => {
    switch (value) {
      case "schedule":
        return <Schedule/>;
      case "clients":
        return <Client/>;
      case "absences":
        return (
          <AbsenceInfo/>
        );
      default:
        return (
          <PersonalData/>
        );
    }
  };

  return (
    <div className="detail-page__tabs-wrapper">
      <TabsComponent value={value} handleChange={handleChange}/>
      <div className="consultant-detail__card">{content()}</div>
    </div>
  );
};

