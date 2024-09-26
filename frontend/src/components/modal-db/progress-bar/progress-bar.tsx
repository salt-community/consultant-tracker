import {useSelector} from "react-redux";
import {RootState} from "../../../store/store.ts";

const ProgressBar = () => {
  const progress = useSelector((state: RootState) => state.modal.progress)

  return (
    <div style={{ width: '100%', backgroundColor: '#e0e0df', borderRadius: '8px', overflow: "hidden" }}>
      <div
        style={{
          width: `${progress}%`,
          height: '20px',
          backgroundColor: 'rgb(110, 172, 218)',
          borderRadius: '8px',
          transition: 'width 0.2s ease-in-out',
        }}
      />
    </div>
  );
};

export default ProgressBar;