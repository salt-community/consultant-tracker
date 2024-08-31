import {createSlice, PayloadAction} from "@reduxjs/toolkit";

interface PaginationState {
  page: number,
  rowsPerPage: number,
  totalItems: number,
}

const initialState: PaginationState = {
  page: 0,
  rowsPerPage: 5,
  totalItems: 0,
}

const paginationSlice = createSlice({
  name: 'pagination',
  initialState,
  reducers: {
    setPage: (state, action: PayloadAction<number>) => {
      state.page = action.payload
    },
    setRowsPerPage: (state, action: PayloadAction<number>) => {
      state.rowsPerPage = action.payload
    },
    setTotalItems: (state, action: PayloadAction<number>) => {
      state.totalItems = action.payload
    },
  }
})

export const {
  setPage,
  setRowsPerPage,
  setTotalItems
} = paginationSlice.actions;
export default paginationSlice.reducer;