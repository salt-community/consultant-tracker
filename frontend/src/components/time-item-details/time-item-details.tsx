import { selectColor } from "../../utils/utils";
import { useEffect } from "react";
import "./time-item-details.css";
import {SingleDetailField} from "../../components";
import { useSelector } from "react-redux";
import { RootState } from "../../store/store";

export const TimeItemDetails=()=> {
  const content = useSelector((state: RootState) => state.ganttChart.modalData);
  const openTimeItemDetails = useSelector(
    (state: RootState) => state.ganttChart.openTimeItemDetails
  );

  useEffect(() => {
  }, [content, openTimeItemDetails]);
  const borderClassName = selectColor(content!.title!.toString());

  return (
    <div
      className={openTimeItemDetails ? "time-details" : "time-details hidden"}
      style={{ border: `1.5px solid ${borderClassName}` }}
    >
      {content && (
        <>
          <h3>{content.title}</h3>
          <hr />
          {/* <SingleDetailField
            title="Client"
            content={content.details.projectName}
          /> */}
          {/* FOR DEMO */}
          <SingleDetailField title="Client" content={content.details.client}/>
          <SingleDetailField
            title="Total Days Selected"
            content={`${content.details.totalDays}`}
          />
          <SingleDetailField
            title="Start Date"
            content={content.start_time.format("ddd, DD-MMM-YYYY")}
          />
          <SingleDetailField
            title="End Date"
            content={content.end_time.format("ddd, DD-MMM-YYYY")}
          />
        </>
      )}
    </div>
  );
}

export default TimeItemDetails;
