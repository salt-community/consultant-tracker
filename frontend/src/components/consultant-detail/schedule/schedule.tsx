"use client";

import { consultantDetailsData } from "@/mockData";
import { ConsultantDetailsDataType, MeetingsType } from "@/types";
import React, { useEffect, useState } from "react";
import "./schedules.css";
import SingleDetailField from "@/components/single-detail-field/single-detail-field";

type Props = {
  meetings: MeetingsType[];
};

const Schedule = ({ meetings }: Props) => {
  const [scheduleData, setScheduleData] = useState<MeetingsType[]>(meetings);
  console.log("shceduled data", scheduleData);
  useEffect(() => {
    setScheduleData(meetings);
  }, [meetings]);

  const formatTitle = (title: string) => {
    switch (title) {
      case "FIRST":
        return "First regular meeting";
      case "SECOND":
        return "Second regular meeting";
      case "THIRD":
        return "Third regular meeting";
      case "FOURTH":
        return "Fourth regular meeting";
      default:
        return "Not Scheduled";
    }
  };

  return scheduleData.length > 0 ? (
    <div className="meetings-schedule__wrapper">
      {scheduleData.map((item, index) => {
        const { year, weekNumber, title } = item;
        return (
          <div key={index} className="meetings-schedule__items">
            <SingleDetailField title="Title" content={formatTitle(title)} />
            <SingleDetailField
              title="Date"
              content={`${year}, Week: ${weekNumber}`}
            />
          </div>
        );
      })}
    </div>
  ) : (
    <div> Time has run out </div>
  );
};

export default Schedule;
