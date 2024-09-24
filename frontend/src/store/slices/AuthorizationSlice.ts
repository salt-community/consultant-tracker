import {createSlice, PayloadAction} from "@reduxjs/toolkit";

interface AuthorizationState {
  showContent: boolean
  authorized: boolean,
  user: string
}

const initialState: AuthorizationState = {
  showContent: false,
  authorized: false,
  user: ""
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
    },
    setUser: (state, action: PayloadAction<string>) => {
      state.user= action.payload
    }
  }
})

export const {setAuthorized, setShowContent, setUser} = authorizationSlice.actions;
export default authorizationSlice.reducer;