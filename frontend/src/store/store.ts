import {configureStore} from "@reduxjs/toolkit";
import dashboardHeaderReducer from './slices/DashboardHeaderSlice'
import filterFieldReducer from './slices/FilterFieldSlice'
import ganttChartReducer from './slices/GanttChartSlice'
import paginationReducer from './slices/PaginationSlice'



export const store = configureStore({
  reducer:{
    dashboardHeader: dashboardHeaderReducer,
    filterField: filterFieldReducer,
    ganttChart: ganttChartReducer,
    pagination: paginationReducer
  }
})

export type RootState =  ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch;