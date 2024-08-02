"use client";
import { ConsultantCalendarType } from "@/types";
import dayjs from "dayjs";
import {
  Dispatch,
  ReactNode,
  SetStateAction,
  createContext,
  useContext,
  useState,
} from "react";

type TableContextType = {
  data: ConsultantCalendarType;
  setData: Dispatch<SetStateAction<ConsultantCalendarType>>;
  filteredData: ConsultantCalendarType,
  setFilteredData: Dispatch<SetStateAction<ConsultantCalendarType>>;
};

const initialData = {
  pageNumber: 0,
  totalPages: 0,
  totalConsultants: 0,
  consultants: [
    {
      id: "",
      fullName: "",
      email: "",
      phoneNumber: "",
      totalDaysStatistics: {
        totalRemainingDays: 0,
        totalWorkedDays: 0,
        totalVacationDaysUsed: 0,
      },
      registeredTimeDtoList: [
        {
          registeredTimeId: "",
          startDate: dayjs(),
          endDate: dayjs(),
          type: "",
          projectName: "",
        },
      ],
    },
  ],
};

const TableContext = createContext<TableContextType>({
  data: initialData,
  setData: () => {},
  filteredData: initialData,
  setFilteredData: () => {}
});

type ContextProviderProps = {
  children: ReactNode;
};

export const TableContextProvider = ({ children }: ContextProviderProps) => {
  const [data, setData] = useState<ConsultantCalendarType>(initialData);
  const [filteredData, setFilteredData] = useState<ConsultantCalendarType>(initialData)
  const value = { data, setData, filteredData, setFilteredData };

  return (
    <TableContext.Provider value={value}>{children}</TableContext.Provider>
  );
};
export const useTableContext = () => useContext(TableContext);
