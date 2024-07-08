"use client";

import { consultantsData } from "@/mockData";
import { ConsultantDataType } from "@/types";
import {
  Dispatch,
  ReactNode,
  SetStateAction,
  createContext,
  useContext,
  useState,
} from "react";

type TableContextType = {
  data: ConsultantDataType[];
  setData: Dispatch<SetStateAction<ConsultantDataType[]>>;
};

const TableContext = createContext<TableContextType>({data: [...consultantsData], setData: ()=>[...consultantsData]});

type ContextProviderProps = {
  children: ReactNode;
};

export const TableContextProvider = ({ children }: ContextProviderProps) => {
  const [data, setData] = useState<ConsultantDataType[]>(consultantsData);
  const value = { data, setData };

  return <TableContext.Provider value={value}>{children}</TableContext.Provider>
  ;
};
export const useTableContext = () => useContext(TableContext);
