import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {ConsultantItemsType} from "../../types";
import {Moment} from "moment/moment";
import moment from "moment";

interface GanttChartState {
  error: string,
  openModal: boolean,
  openTimeItemDetails: boolean,
  items: ConsultantItemsType[],
  groups: string[],
  loading: boolean,
  id: string,
  modalData?: ConsultantItemsType,
  redDaysSE: Moment[],
  redDaysNO: Moment[],
  selectedId: string
}

const initialState: GanttChartState = {
  error: "",
  openModal: false,
  openTimeItemDetails: false,
  items: [],
  groups: [],
  loading: true,
  id: "",
  modalData: undefined,
  redDaysSE: [],
  redDaysNO: [],
  selectedId: ""
}

const dashboardHeaderSlice = createSlice({
  name: 'ganttChart',
  initialState,
  reducers: {
    setError: (state, action: PayloadAction<string>) => {
      state.error = action.payload
    },
    setOpenModal: (state, action: PayloadAction<boolean>) => {
      state.openModal = action.payload
    },
    setOpenTimeItemDetails: (state, action: PayloadAction<boolean>) => {
      state.openTimeItemDetails = action.payload
    },
    setItems: (state, action: PayloadAction<ConsultantItemsType[]>) => {
      state.items = Object.assign(state.items, action.payload);
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
    setModalData: (state, action: PayloadAction<ConsultantItemsType>) => {
      state.modalData = {start_time: action.payload.start_time, end_time: action.payload.end_time, title: action.payload.title, details: action.payload.details, id: action.payload.id, group: action.payload.group};
    },
    setRedDaysSE: (state, action: PayloadAction<moment.Moment[]>) => {
      state.redDaysSE = action.payload
    },
    setRedDaysNO: (state, action: PayloadAction<moment.Moment[]>) => {
      state.redDaysNO = action.payload
    },
    setSelectedId: (state, action: PayloadAction<string>) => {
      state.selectedId = action.payload
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
  setOpenModal,
  setOpenTimeItemDetails,
  setError,
  setSelectedId
} = dashboardHeaderSlice.actions;
export default dashboardHeaderSlice.reducer;