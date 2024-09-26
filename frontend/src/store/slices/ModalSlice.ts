import {createSlice, PayloadAction} from "@reduxjs/toolkit";

interface ModalState {
  modalOpen: boolean,
  progressBarOpen: boolean,
  progress: number
}

const initialState: ModalState = {
  modalOpen: false,
  progressBarOpen: false,
  progress: 0
}

const modalSlice = createSlice({
  name: 'modal',
  initialState,
  reducers: {
    setModalOpen: (state, action: PayloadAction<boolean>) => {
      state.modalOpen = action.payload
    },
    setProgressBarOpen: (state, action: PayloadAction<boolean>) => {
      state.progressBarOpen = action.payload
    },
    setProgress: (state, action: PayloadAction<number>) => {
      state.progress = action.payload
    }
  }
})

export const {setModalOpen,setProgress,
  setProgressBarOpen} = modalSlice.actions;
export default modalSlice.reducer;