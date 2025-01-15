export type InfographicResponseType = {
  totalConsultants: number,
  ptsConsultants: number
  totalPgpConsultants: number
}

export type InfographicDataType = {
  title: string,
  amount: number,
  variant: string
}

export type ClientDataType = {
  name: string;
  startDate: string;
  endDate: string;
};


export type MeetingsType = {
  year: number;
  weekNumber: number;
  title: string;
};

export type ConsultantItemsType = {
  start_time: moment.Moment,
  itemProps?: Omit<React.HTMLAttributes<HTMLDivElement>, 'defaultValue'> | undefined;
  end_time: moment.Moment,
  details: {
    responsiblePt: string,
    country: string,
    totalDays: number,
    name: string,
    client: string,
    totalDaysStatistics: {
      totalRemainingHours: number,
      totalWorkedHours: number,
      totalWorkedDays: number,
      totalVacationDaysUsed: number,
      totalRemainingDays: number
    },
    projectName: string
  },
  id: string,
  title: string,
  group: string
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
  country: string,
  gitHubImgUrl: string
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
  startDate: moment.Moment,
  endDate: moment.Moment,
  type: string,
  days: number,
  projectName: string
}

export type ClientsAndPtsListResponseType = {
  pts: string[],
  clients: string[]
}

export type RedDaysResponseType = {
  redDaysSE: string[],
  redDaysNO: string[],
}