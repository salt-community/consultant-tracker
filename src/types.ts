export type InfographicType = {
  title: string;
  amount: number;
};

export type ConsultantDataType = {
  id: string;
  name: string;
  client: string;
  status: string;
  details: string;
};

export type HeaderCellsType = {
  disablePadding: boolean;
  id: keyof ConsultantDataType;
  label: string;
  numeric: boolean;
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
  id: string,
  name: string
  client: ClientDataType[],
  status: string,
  absence: AbsenceType[],
  address: string,
  phone: string,
  startDate: string,
  remainsHours: number,
  email: string,
  meetings: MeetingsType[],
}