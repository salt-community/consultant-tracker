import "./absence-info.css";
import SingleDetailField from "@/components/single-detail-field/single-detail-field";
import {TotalDaysStatisticsType} from "@/types";

type Props = {
  totalDaysStatistics: TotalDaysStatisticsType;
};

const AbsenceInfo = ({totalDaysStatistics}: Props) => {
  const {
    totalVacationDaysUsed,
    totalSickDays,
    totalParentalLeaveDays,
    totalVABDays,
    totalUnpaidVacationDays
  } = totalDaysStatistics;
  return (
    <div className="absence-info__wrapper">
      <SingleDetailField title="Total vacation days used" content={`${totalVacationDaysUsed}`}/>
      <SingleDetailField title="Total sick days used" content={`${totalSickDays}`}/>
      <SingleDetailField title="Total parental leave days used" content={`${totalParentalLeaveDays}`}/>
      <SingleDetailField title="Total VAB days used" content={`${totalVABDays}`}/>
      <SingleDetailField title="Total unpaid leave days used" content={`${totalUnpaidVacationDays}`}/>
    </div>
  );
};

export default AbsenceInfo;
