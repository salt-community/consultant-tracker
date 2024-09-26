import { RootState } from "../../../store/store";
import SingleDetailField from "../../single-detail-field/single-detail-field";
import "./absence-info.css";
import {useSelector} from "react-redux";

const AbsenceInfo = () => {
  const totalDaysStatistics = useSelector((state: RootState) => state.basicInfo.personalData!.totalDaysStatistics)
  const {
    totalVacationDaysUsed,
    totalSickDays,
    totalParentalLeaveDays,
    totalVABDays,
    totalUnpaidVacationDays,
  } = totalDaysStatistics;
  return (
    <div className="absence-info__container">
      <div className="absence-info__wrapper">
        <h4>Total days used</h4>
        <div className="absence-info__content">
          <SingleDetailField
            title="Vacation"
            content={`${totalVacationDaysUsed}`}
          />
          <SingleDetailField title="Sick" content={`${totalSickDays}`}/>
          <SingleDetailField
            title="Parental leave"
            content={`${totalParentalLeaveDays}`}
          />
          <SingleDetailField title="VAB" content={`${totalVABDays}`}/>
          <SingleDetailField
            title="Unpaid leave"
            content={`${totalUnpaidVacationDays}`}
          />
        </div>
      </div>
    </div>
  );
};

export default AbsenceInfo;
