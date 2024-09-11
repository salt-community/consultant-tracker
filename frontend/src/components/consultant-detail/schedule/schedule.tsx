"use client";

import "./schedules.css";
import SingleDetailField from "@/components/single-detail-field/single-detail-field";
import {useSelector} from "react-redux";
import {RootState} from "@/store/store";


const Schedule = () => {
  const scheduleData = useSelector((state: RootState) => state.basicInfo.personalData!.meetings)

  const formatTitle = (title: string) => {
    switch (title) {
      case "FIRST":
        return "1st regular meeting";
      case "SECOND":
        return "2nd regular meeting";
      case "THIRD":
        return "3rd regular meeting";
      case "FOURTH":
        return "4th regular meeting";
      default:
        return "Not Scheduled";
    }
  };

  return scheduleData.length > 0 ? (
    <div className="meetings-schedule__wrapper">
      {scheduleData.map((item, index) => {
        const {year, weekNumber, title} = item;
        return (
          <div key={index} className="meetings-schedule__items">
                <SingleDetailField title={index == 0 ? "Title" : undefined} content={formatTitle(title)}/>
                <SingleDetailField
                    title={index == 0 ? "Date" : undefined}
                    content={`${year}, Week: ${weekNumber}`}
                />
          </div>
        );
      })}
    </div>
  ) : (
    <div> No meetings to show </div>
  );
};

export default Schedule;
