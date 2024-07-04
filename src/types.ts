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
}

export type HeaderCellsType = {
  disablePadding: boolean;
  id: keyof ConsultantDataType;
  label: string;
  numeric: boolean;
};
