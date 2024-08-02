import dayjs, { Dayjs } from "dayjs";
import React from "react";

export type InfographicType = {
  title: string;
  amount: number;
};

export type ConsultantDataType = {
  id: string;
  name: string;
  clientId: string;
  client: string;
  pt: string
};

export type HeaderCellsType = {
  id: keyof ConsultantFetchType;
  label: string;
};

export type ClientDataType = {
  id: string;
  name: string;
  startDate: string;
  endDate: string;
};

export type AbsenceType = {
  description: string;
  startDateAbsence: string;
  endDateAbsence?: string;
  absenceHours?: number;
};

export type MeetingsType = {
  id: string;
  date: string;
  title: string;
  description: string;
};

export type ConsultantDetailsDataType = {
  id: string;
  name: string;
  client: ClientDataType[];
  pt: string;
  absence: AbsenceType[];
  github: string;
  phone: string;
  startDate: string;
  remainingHours: number;
  email: string;
  meetings: MeetingsType[];
};

export type ContactPeopleType = {
  id: string;
  name: string;
  phone: string;
  email: string;
};

export type CompanyConsultantsType = {
  id: string;
  name: string;
};

export type ClientsDetailsDataType = {
  id: string;
  name: string;
  contactPeople: ContactPeopleType[];
  listOfConsultants: CompanyConsultantsType[];
};

export type ConsultantsCalendarType = {
  id: number;
  title: string;
};

export type ConsultantItemsType = {
  id: number;
  group: number;
  title?: React.ReactNode | undefined;
  start_time: Dayjs;
  end_time: Dayjs;
  canMove?: boolean | undefined;
  canResize?: boolean | "left" | "right" | "both" | undefined;
  canChangeGroup?: boolean | undefined;
  className?: string | undefined;
  style?: React.CSSProperties | undefined;
  itemProps?: React.HTMLAttributes<HTMLDivElement> | undefined;
};

export type ConsultantCalendarType = {
  pageNumber: number,
  totalPages: number,
  totalConsultants: number,
  consultants: ConsultantFetchType[]
};

export type ConsultantFetchType = {
  id: string,
  fullName: string,
  email: string,
  phoneNumber: string,
  // totalDaysStatistics: TotalDaysStatisticsType,
  // registeredTimeDtoList: RegisteredTimeItemType[]
}

  export type TotalDaysStatisticsType = {
    totalRemainingDays: number,
    totalWorkedDays: number,
    totalVacationDaysUsed: number
}

export type RegisteredTimeItemType = {
  registeredTimeId: string,
  startDate: dayjs.Dayjs,
  endDate: dayjs.Dayjs,
  type: string,
  projectName: string
}
