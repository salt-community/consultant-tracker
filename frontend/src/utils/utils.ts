import {
  ConsultantCalendarType,
  ConsultantFetchType, ConsultantItemsType,
  RegisteredTimeItemType,
} from "../types";
import moment, {Moment} from "moment/moment";

export const user = "Josefin Stål"
// export const user = "Stella Asplund" // FOR DEMO
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

export const legend = [
  {
    color: "#6EACDA",
    description: "Working hours (Konsult-tid)"
  },
  {
    color: "#7bc46e",
    description: "Vacation (Semester)"
  },
  {
    color: "#F3FEB8",
    description: "Sick leave (Sjuk)"
  },
  {
    color: "#8ddfc2",
    description: "Off-duty (Tjänstledig)"
  },
  {
    color: "#a4a4a4",
    description: "Remaining Days (Dagar kvar)"
  }, 
  {
    color: "#000000",
    description: "Parental leave (Föräldraledig/VAB)"
  },
  {
    color: "#EF5A6F",
    description: "No registered time (Ej registrerad tid)"
  }
]
// export const redDaysAndWeekends =[
//   {
//     color: "#f5d1d7",
//     description: "Weekends "
//   },
//   {
//     color: "#F5D4A6FF",
//     description: "Holiday Norway "
//   },
//   {
//     color: "#F4AB9FFF",
//     description: "Holiday Sweden "
//   },
// ]


export const mapGroups = (res: any) => {
  return res.consultants.map((el: ConsultantFetchType) => {
    const title =
      el.country === "NO"
        ? el.fullName + ` (${el.country})`
        : el.fullName;
    return {id: el.id, title: title};
  });
}

export const mapConsultantsToCalendarItems = (res: ConsultantCalendarType): ConsultantItemsType[] => {
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
        start_time: moment(item.startDate),
        end_time: moment(item.endDate),
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
  timeStart: number,
  timeEnd: number,
  redDaysSE: Moment[],
  redDaysNO: Moment[]
) => {
  const currentTimeStart = moment(timeStart);
  const currentTimeEnd = moment(timeEnd);

  const classes = [];
  for (const holiday of redDaysSE) {
    if (
      holiday.isSame(currentTimeStart, "day") &&
      holiday.isSame(currentTimeEnd, "day")
    ) {
      classes.push("holidaySE");
    }
  }
  for (const holiday of redDaysNO) {
    if (
      holiday.isSame(currentTimeStart, "day") &&
      holiday.isSame(currentTimeEnd, "day")
    ) {
      classes.push("holidayNO");
    }
  }
  return classes;
};