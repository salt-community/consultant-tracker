import dayjs from "dayjs";
import { ConsultantItemsType } from "./types";

export const getDatesForRemainingTime = (
  id: number,
  items: ConsultantItemsType[]
) => {
  const matchingDates = items
    .filter((i) => i.group === id)
    .map((d) => d.start_time);
  const workedTime = matchingDates.length * 8;
  const remainingHours = 88 - workedTime;
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

  const tempEndDate = dayjs(startDate)
    .set("date", startDate.date() + remainingDays - 1)
    .set("hours", 23)
    .set("minutes", 59 + timezoneOffset)
    .set("seconds", 59);

  const endDate = accountForNonWorkingDays(startDate, tempEndDate);

  return JSON.stringify({
    start_date: startDate,
    end_date: endDate,
    remainingHours: remainingHours,
  });
};

function accountForNonWorkingDays(
  startDate: dayjs.Dayjs,
  tempEndDate: dayjs.Dayjs
) {
  let nonWorkDaysCount = 0;
  let isWeekend = false;

  while (startDate < tempEndDate) {
    var day = startDate.day();
    isWeekend = day === 6 || day === 0;
    if (isWeekend) {
      nonWorkDaysCount++;
    } // return immediately if weekend found
    startDate.date(startDate.day() + 1);
  }
  console.log("tempEndDate: ", tempEndDate);
  const endDate = dayjs(tempEndDate).set('date', tempEndDate.date() + nonWorkDaysCount);
  console.log("adjusted end date: ", endDate);
  return endDate;
}
