import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {ConsultantFetchType} from "../../types";

interface BasicInfoState {
  personalData: ConsultantFetchType | undefined
}

const initialState: BasicInfoState = {
 personalData: undefined
}

const basicInfoSlice = createSlice({
  name: 'basicInfo',
  initialState,
  reducers: {
    setPersonalData: (state, action: PayloadAction<ConsultantFetchType>) => {
      state.personalData = action.payload
    }
  }
})

export const {setPersonalData} = basicInfoSlice.actions;
export default basicInfoSlice.reducer;