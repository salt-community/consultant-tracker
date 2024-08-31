import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {ConsultantItemsType} from "@/types";
import {Moment} from "moment/moment";
import moment from "moment";

interface GanttChartState {
  error: string,
  open: boolean,
  items: ConsultantItemsType[],
  groups: string[],
  loading: boolean,
  id: string,
  modalData?: ConsultantItemsType,
  redDaysSE: Moment[],
  redDaysNO: Moment[]
}

const initialState: GanttChartState = {
  error: "",
  open: false,
  items: [],
  groups: [],
  loading: true,
  id: "",
  modalData: undefined,
  redDaysSE: [],
  redDaysNO: []
}

const dashboardHeaderSlice = createSlice({
  name: 'ganttChart',
  initialState,
  reducers: {
    setError: (state, action: PayloadAction<string>) => {
      state.error = action.payload
    },
    setOpen: (state, action: PayloadAction<boolean>) => {
      state.open = action.payload
    },
    setItems: (state, action: PayloadAction<any>) => {
      state.items = action.payload
    },
    setGroups: (state, action: PayloadAction<string[]>) => {
      state.groups = action.payload
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload
    },
    setId: (state, action: PayloadAction<string>) => {
      state.id = action.payload
    },
    setModalData: (state, action: PayloadAction<any>) => {
      state.modalData = action.payload
    },
    setRedDaysSE: (state, action: PayloadAction<moment.Moment[]>) => {
      state.redDaysSE = action.payload
    },
    setRedDaysNO: (state, action: PayloadAction<moment.Moment[]>) => {
      state.redDaysNO = action.payload
    },
  }
})

export const {
  setRedDaysSE,
  setRedDaysNO,
  setModalData,
  setId,
  setLoading,
  setGroups,
  setItems,
  setOpen,
  setError
} = dashboardHeaderSlice.actions;
export default dashboardHeaderSlice.reducer;