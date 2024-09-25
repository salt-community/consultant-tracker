import { configureStore } from "@reduxjs/toolkit";
import dashboardHeaderReducer from "./slices/DashboardHeaderSlice";
import filterFieldReducer from "./slices/FilterFieldSlice";
import ganttChartReducer from "./slices/GanttChartSlice";
import paginationReducer from "./slices/PaginationSlice";
import basicInfoReducer from "./slices/BasicInfoSlice";
import authorizationReducer from "./slices/AuthorizationSlice";

export const store = configureStore({
  reducer: {
    dashboardHeader: dashboardHeaderReducer,
    filterField: filterFieldReducer,
    ganttChart: ganttChartReducer,
    pagination: paginationReducer,
    basicInfo: basicInfoReducer,
    authorization: authorizationReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
