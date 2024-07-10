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

type ClientsContextType = {
  name: string;
  data: ClientsDetailsDataType[];
  setData: Dispatch<SetStateAction<ClientsDetailsDataType[]>>
  setName: Dispatch<SetStateAction<string>>
};

const ClientsContext = createContext<ClientsContextType>({
  name: "",
  data: [],
  setName: () => "",
  setData: () => [],
});

type ContextProviderProps = {
  children: ReactNode;
};

export const ClientsContextProvider = ({ children }: ContextProviderProps) => {
  const [name, setName] = useState<string>("");
  const [data, setData] = useState<ClientsDetailsDataType[]>(
    clientsData
  );
  const value = { name, setName, data, setData };

  return (
    <ClientsContext.Provider value={value}>{children}</ClientsContext.Provider>
  );
};
export const useClientsContext = () => useContext(ClientsContext);
