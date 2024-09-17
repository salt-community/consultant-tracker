import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {InfographicDataType} from "../../types";

interface DashboardHeaderState {
  infographicData: InfographicDataType[]
}

const initialState: DashboardHeaderState = {
  infographicData: []
}

const dashboardHeaderSlice = createSlice({
  name: 'dashboardHeader',
  initialState,
  reducers: {
    setInfographicData: (state, action: PayloadAction<InfographicDataType[]>) => {
      state.infographicData = action.payload
    }
  }
})

export const {setInfographicData} = dashboardHeaderSlice.actions;
export default dashboardHeaderSlice.reducer;