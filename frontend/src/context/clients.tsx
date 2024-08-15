"use client";

import {clientsData} from "@/mockData";
import { ClientsDetailsDataType} from "@/types";
import {
  Dispatch,
  ReactNode,
  SetStateAction,
  createContext,
  useContext,
  useState,
} from "react";

export type ClientsContextType = {
  fullName: string;
  data: ClientsDetailsDataType[];
  setData: Dispatch<SetStateAction<ClientsDetailsDataType[]>>
  setFullName: Dispatch<SetStateAction<string>>
};

export const ClientsContext = createContext<ClientsContextType>({
  fullName: "",
  data: [],
  setFullName: () => "",
  setData: () => [],
});

type ContextProviderProps = {
  children: ReactNode;
};

export const ClientsContextProvider = ({ children }: ContextProviderProps) => {
  const [fullName, setFullName] = useState<string>("");
  const [data, setData] = useState<ClientsDetailsDataType[]>(
    clientsData
  );
  const value = { fullName, setFullName, data, setData };

  return (
    <ClientsContext.Provider value={value}>{children}</ClientsContext.Provider>
  );
};
export const useClientsContext = () => useContext(ClientsContext);
