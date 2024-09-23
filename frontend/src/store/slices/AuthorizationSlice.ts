import {createSlice, PayloadAction} from "@reduxjs/toolkit";

interface AuthorizationState {
  showContent: boolean
  authorized: boolean,
}

const initialState: AuthorizationState = {
  showContent: false,
  authorized: false
}

const authorizationSlice = createSlice({
  name: 'authorization',
  initialState,
  reducers: {
    setAuthorized: (state, action: PayloadAction<boolean>) => {
      state.authorized= action.payload
    },
    setShowContent: (state, action: PayloadAction<boolean>) => {
      state.showContent= action.payload
    }
  }
})

export const {setAuthorized, setShowContent} = authorizationSlice.actions;
export default authorizationSlice.reducer;