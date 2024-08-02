"use client";
import { ConsultantCalendarType } from "@/types";
import dayjs, { Dayjs } from "dayjs";
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
  setData: () => {
    initialData;
  },
});

type ContextProviderProps = {
  children: ReactNode;
};

export const TableContextProvider = ({ children }: ContextProviderProps) => {
  const [data, setData] = useState<ConsultantCalendarType>(initialData);
  const value = { data, setData };

  return (
    <TableContext.Provider value={value}>{children}</TableContext.Provider>
  );
};
export const useTableContext = () => useContext(TableContext);
