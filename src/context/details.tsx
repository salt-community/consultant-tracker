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
  name: string;
  data: ConsultantDetailsDataType[];
  setName: Dispatch<SetStateAction<string>>;
  setData: Dispatch<SetStateAction<ConsultantDetailsDataType[]>>;
};

export const DetailsContext = createContext<DetailsContextType>({
  name: "",
  data: [],
  setName: () => "",
  setData: () => [],
});

type ContextProviderProps = {
  children: ReactNode;
};

export const DetailsContextProvider = ({ children }: ContextProviderProps) => {
  const [name, setName] = useState<string>("");
  const [data, setData] = useState<ConsultantDetailsDataType[]>(
    consultantDetailsData
  );
  const value = { name, setName, data, setData };

  return (
    <DetailsContext.Provider value={value}>{children}</DetailsContext.Provider>
  );
};
export const useDetailsContext = () => useContext(DetailsContext);
