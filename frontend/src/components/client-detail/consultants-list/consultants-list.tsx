import { useClientsContext } from "@/context/clients";
import SingleConsultant from "@/components/client-detail/consultants-list/single-consultant/single-consultant";
import { usePathname } from "next/navigation";
import Timeline from "react-calendar-timeline";
import "react-calendar-timeline/lib/Timeline.css";
import moment from "moment";
import { consultantsCalendar, consultantItems } from "@/mockData";
import { useState } from "react";
import { ConsultantItemsType } from "@/types";
import dayjs from "dayjs";

const ConsultantsList = () => {
  const client = useClientsContext();
  const idParam = usePathname().split("/").pop();
  const singleClient = client.data.filter((el) => el.id === idParam)[0];
  const [items, setItems] = useState<ConsultantItemsType[]>(consultantItems);

  const getDatesForRemainingTime = (id: number) => {
    const matchingDates = items
      .filter((i) => i.group === id)
      .map((d) => d.start_time);
    const workedTime = matchingDates.length * 8;
    const remainingHours = 48 - workedTime;
    const remainingDays = remainingHours / 8;
    const lastWorkedDay = dayjs(matchingDates[matchingDates.length - 1]);
    const timezoneOffset = lastWorkedDay.utcOffset();
    const startDate = dayjs()
      .set("day", lastWorkedDay.day() + 1)
      .set("month", lastWorkedDay.month())
      .set("year", lastWorkedDay.year())
      .set("hours", 0)
      .set("minutes", 0 + timezoneOffset)
      .set("seconds", 0);

    const endDate = dayjs(startDate)
      .set("date", startDate.date() + remainingDays - 1)
      .set("hours", 23)
      .set("minutes", 59 + timezoneOffset)
      .set("seconds", 59);

    return JSON.stringify({
      start_date: startDate,
      end_date: endDate,
      remainingHours: remainingHours,
    });
  };

  console.table("addEstimatedTimeLeft: " + getDatesForRemainingTime(1));

  return (
    <div>
      <div>
        <Timeline
          groups={consultantsCalendar}
          items={items}
          defaultTimeStart={moment().add(-17, "day")}
          defaultTimeEnd={moment().add(4, "day")}
        />
      </div>
      {singleClient.listOfConsultants.map((consultant) => {
        const { name, id } = consultant;
        return <SingleConsultant name={name} id={id} key={id} />;
      })}
    </div>
  );
};

export default ConsultantsList;
