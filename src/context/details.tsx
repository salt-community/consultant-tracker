"use client";
import {
  Dispatch,
  ReactNode,
  SetStateAction,
  createContext,
  useContext,
  useState,
} from "react";

type DetailsContextType = {
  name: string;
  setName: Dispatch<SetStateAction<string>>;
};

const DetailsContext = createContext<DetailsContextType>({name: "", setName: () => ""});

type ContextProviderProps = {
  children: ReactNode;
};

export const DetailsContextProvider = ({children}: ContextProviderProps) => {
  const [name, setName] = useState<string>("");
  const value = {name, setName};

  return <DetailsContext.Provider value={value}>{children}</DetailsContext.Provider>
    ;
};
export const useDetailsContext = () => useContext(DetailsContext);
