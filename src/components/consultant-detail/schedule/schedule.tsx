"use client";

import {consultantDetailsData} from "@/mockData";
import {ConsultantDetailsDataType} from "@/types";
import {usePathname} from "next/navigation";
import React, {useEffect, useState} from "react";
import './schedules.css'
import { PiPencilSimpleLineThin } from "react-icons/pi";

const Schedule = () => {
  const idParam = usePathname().split("/").pop();
  const [scheduleData, setScheduleData] = useState<ConsultantDetailsDataType>(
    consultantDetailsData[0]
  );

  useEffect(() => {
    const consultantById = consultantDetailsData.filter(
      (el) => el.id == idParam
    );
    setScheduleData(consultantById[0]);
  }, [idParam]);

  return (
    scheduleData && (
      <div className="meetings-schedule__wrapper">
        <PiPencilSimpleLineThin style={{alignSelf: "end"}}/>
        {scheduleData.meetings.map((item) => {
          const {id, date, title, description} = item;
          return (<div key={id}>
              <p>Title: {title}</p>
              <p>Date : {date}</p>
            <p>Description: {description}</p>
          </div>)
        })}
      </div>
    )
  )
    ;
};

export default Schedule;
