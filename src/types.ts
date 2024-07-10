export type InfographicType = {
  title: string;
  amount: number;
};

export type ConsultantDataType = {
  id: string;
  name: string;
  clientId: string,
  client: string;
  status: string;
};

export type HeaderCellsType = {
  id: keyof ConsultantDataType;
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
  id: string,
  name: string
  client: ClientDataType[],
  status: string,
  absence: AbsenceType[],
  github: string,
  phone: string,
  startDate: string,
  remainingHours: number,
  email: string,
  meetings: MeetingsType[],
}

export type ContactPeopleType = {
  id: string,
  name: string,
  phone: string,
  email: string
}

export type CompanyConsultantsType = {
  id: string,
  name: string
}

export type ClientsDetailsDataType = {
  id: string,
  name: string,
  contactPeople: ContactPeopleType[],
  listOfConsultants: CompanyConsultantsType[]
}
