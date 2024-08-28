import dayjs, { Dayjs } from "dayjs";
import React from "react";

// export type InfographicType = {
//   title: string;
//   amount: number;
// };

// export type ConsultantDataType = {
//   id: string;
//   name: string;
//   clientId: string;
//   client: string;
//   pt: string
// };

// export type HeaderCellsType = {
//   id: keyof ConsultantFetchType;
//   label: string;
// };
export type InfographicResponseType={
  totalConsultants: number,
  ptsConsultants: number
}
export type InfographicDataType ={
  title: string,
  amount: number,
  variant: string
}

export type ClientDataType = {
  // id: string;
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
  year: number;
  weekNumber: number;
  title: string;
};

export type ConsultantDetailsDataType = {
  id: string;
  name: string;
  client: ClientDataType[];
  pt: string;
  absence: AbsenceType[];
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
  id: string;
  group: string;
  title?: React.ReactNode | undefined;
  start_time: Dayjs;
  end_time: Dayjs;
  canMove?: boolean | undefined;
  details: detailsType,
  onDoubleClick?: (()=> void) |undefined
  onItemClick?: (()=> void) |undefined
  canResize?: boolean | "left" | "right" | "both" | undefined;
  canChangeGroup?: boolean | undefined;
  className?: string | undefined;
  style?: React.CSSProperties | undefined;
  itemProps?: React.HTMLAttributes<HTMLDivElement> | undefined;
};
export type detailsType={
  name: string,
  responsiblePt: string,
  client: string,
  country: string,
  totalDays: number,
  projectName: string,
  totalDaysStatistics: TotalDaysStatisticsType,
}

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
  responsiblePt: string,
  totalDaysStatistics: TotalDaysStatisticsType,
  registeredTimeDtoList: RegisteredTimeItemType[],
  clientsList: ClientDataType[],
  meetings: MeetingsType[],
  client: string,
  country: string
}

  export type TotalDaysStatisticsType = {
    totalRemainingDays: number,
    totalWorkedDays: number,
    totalVacationDaysUsed: number,
    totalSickDays: number,
    totalParentalLeaveDays: number,
    totalVABDays: number,
    totalUnpaidVacationDays: number,
    totalRemainingHours: number,
    totalWorkedHours: number
}

export type RegisteredTimeItemType = {
  registeredTimeId: string,
  startDate: dayjs.Dayjs,
  endDate: dayjs.Dayjs,
  type: string,
  days: number,
  projectName: string
}

export type AllClientsAndResponsiblePtResponse ={
  pts: string[],
  clients: string[]
}
export type RedDaysResponseType ={
  redDaysSE: string[],
  redDaysNO: string[],
}