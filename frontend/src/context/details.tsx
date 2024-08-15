"use client";

import { consultantDetailsData } from "@/mockData";
import { ConsultantDetailsDataType } from "@/types";
import {
  Dispatch,
  ReactNode,
  SetStateAction,
  createContext,
  useContext,
  useState,
} from "react";

export type DetailsContextType = {
  fullName: string;
  data: ConsultantDetailsDataType[];
  setFullName: Dispatch<SetStateAction<string>>;
  setData: Dispatch<SetStateAction<ConsultantDetailsDataType[]>>;
};

export const DetailsContext = createContext<DetailsContextType>({
  fullName: "",
  data: [],
  setFullName: () => "",
  setData: () => [],
});

type ContextProviderProps = {
  children: ReactNode;
};

export const DetailsContextProvider = ({ children }: ContextProviderProps) => {
  const [fullName, setFullName] = useState<string>("");
  const [data, setData] = useState<ConsultantDetailsDataType[]>(
    consultantDetailsData
  );
  const value = { fullName, setFullName, data, setData };

  return (
    <DetailsContext.Provider value={value}>{children}</DetailsContext.Provider>
  );
};
export const useDetailsContext = () => useContext(DetailsContext);
