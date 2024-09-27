import './modal-db.css'
import {populateDB} from "../../api.ts";
import ProgressBar from "./progress-bar/progress-bar.tsx";
import {INTERVAL, MAX_TIME, template} from "../../constants.ts";

import {setModalOpen, setProgress, setProgressBarOpen} from "../../store/slices/ModalSlice.ts";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../store/store.ts";

import {Modal} from "@mui/material";
import Button from "@mui/material/Button";
import toast from "react-hot-toast";
import {useAuth} from "@clerk/clerk-react";


export const ModalComponent = () => {
  const dispatch = useDispatch();
  const modalOpen = useSelector((state: RootState) => state.modal.modalOpen)
  const progressBarOpen = useSelector((state: RootState) => state.modal.progressBarOpen)
  const {getToken, signOut} = useAuth()

  const handlePopulateDB = async () => {
    let elapsed = 0;
    const startTime = Date.now();

    dispatch(setProgressBarOpen(true))
    let token: string | null = "";
    token = await getToken({template});
    if (token === null) {
      await signOut();
      return;
    }

    const progressInterval = setInterval(() => {
      const currentTime = Date.now();
      elapsed = currentTime - startTime;

      const newProgress = Math.min((elapsed / MAX_TIME) * 100, 100);
      dispatch(setProgress(newProgress));

      if (newProgress >= 90) {
        dispatch(setProgress(90));
      }
    }, INTERVAL);

    populateDB(token).then(res => {
      if (res.status >= 300) {
        toast.error("Failed to populate db.")
      }
      if (res.status === 200) {
        toast.success("Successfully populated db.")
      }
    }).finally(() => {
      dispatch(setProgress(100));
      clearInterval(progressInterval);
      setTimeout(()=>{
        dispatch(setModalOpen(false))
        dispatch(setProgressBarOpen(false))
        dispatch(setProgress(0));
      }, 500);

    })
  }
  return (
    <Modal
      open={modalOpen}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
      disableScrollLock={ true }
    >
      <div className="modal-content">
        <div>
        {!progressBarOpen && <p>You are about to populate the DB</p>}
          <p>This might take up to 15 minutes</p>
          <p>After the process is done please refresh the page.</p>
          {!progressBarOpen && <p>Do you want to proceed?</p>}
        </div>
        {progressBarOpen ? <ProgressBar />: <div className="modal-buttons__wrapper">
          <Button onClick={handlePopulateDB} variant="outlined" color="success">Yes</Button>
          <Button onClick={() => dispatch(setModalOpen(false))} variant="outlined" color="error">No</Button></div>}
      </div>
    </Modal>
  );
};
