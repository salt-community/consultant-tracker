import {
  ClientDataType,
  ConsultantCalendarType,
  ConsultantFetchType,
  RegisteredTimeItemType,
} from "@/types";
import dayjs, { Dayjs } from "dayjs";
import moment from "moment/moment";

export const selectColor = (type: string) => {
  switch (type) {
    case "Konsult-tid":
    case "Egen administration":
      return "#6EACDA";
    case "Semester":
      return "#7bc46e";
    case "Sjuk":
      return "#F3FEB8";
    case "Tjänstledig":
      return "#8ddfc2";
    case "Remaining Days":
      return "#a4a4a4";
    case "Föräldraledig":
    case "VAB":
      return "#000000";
    default:
      return "#EF5A6F";
  }
};

export const encodeString = (value: string[], prefix: string) => {
  return value
    .map((p) => `${prefix}=${encodeURIComponent(p)}`)
    .join("&")
    .replaceAll("-", "%2D")
    .replaceAll(".", "%2E");
};

export const mapConsultantsToCalendarItems = (res: ConsultantCalendarType) => {
  return res.consultants.flatMap((el: ConsultantFetchType) => {
    return el.registeredTimeDtoList.map((item: RegisteredTimeItemType) => {
      const {
        totalRemainingDays,
        totalWorkedDays,
        totalVacationDaysUsed,
        totalRemainingHours,
        totalWorkedHours,
      } = el.totalDaysStatistics;
      return {
        id: item.registeredTimeId,
        group: el.id,
        title: item.type,
        start_time: dayjs(item.startDate),
        end_time: dayjs(item.endDate),
        details: {
          name: el.fullName,
          responsiblePt: el.responsiblePt,
          client: el.client,
          country: el.country,
          projectName: item.projectName,
          totalDaysStatistics: {
            totalRemainingDays: totalRemainingDays,
            totalWorkedDays: totalWorkedDays,
            totalVacationDaysUsed: totalVacationDaysUsed,
            totalRemainingHours: totalRemainingHours,
            totalWorkedHours: totalWorkedHours,
          },
          totalDays: item.days,
        },
        itemProps: {
          style: {
            zIndex: 1,
            background: selectColor(item.type),
            outline: "none",
            borderColor: selectColor(item.type),
            borderRightWidth: "0",
          },
        },
      };
    });
  });
};

export const verticalLineClassNamesForTime = (
  timeStart,
  timeEnd,
  redDaysSE,
  redDaysNO
) => {
  const currentTimeStart = moment(timeStart);
  const currentTimeEnd = moment(timeEnd);

  let classes = [];
  for (let holiday of redDaysSE) {
    if (
      holiday.isSame(currentTimeStart, "day") &&
      holiday.isSame(currentTimeEnd, "day")
    ) {
      classes.push("holidaySE");
    }
  }
  for (let holiday of redDaysNO) {
    if (
      holiday.isSame(currentTimeStart, "day") &&
      holiday.isSame(currentTimeEnd, "day")
    ) {
      classes.push("holidayNO");
    }
  }
  return classes;
};



