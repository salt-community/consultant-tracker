import "./absence-info.css";
import SingleDetailField from "@/components/single-detail-field/single-detail-field";

type Props = {
  vacationDaysUsed: number;
  sickDaysUsed?: number;
  parentalLeaveUsed?: number;
  vabDaysUsed?: number;
  unpaidLeaveUsed?: number
};

const AbsenceInfo = ({vacationDaysUsed, sickDaysUsed, parentalLeaveUsed, vabDaysUsed, unpaidLeaveUsed}: Props) => {
  return (
    <div className="absence-info__wrapper">
      <SingleDetailField title="Total vacation days used" content={`${vacationDaysUsed}`}/>
      <SingleDetailField title="Total sick days used" content={`${sickDaysUsed}`}/>
      <SingleDetailField title="Total parental leave days used" content={`${parentalLeaveUsed}`}/>
      <SingleDetailField title="Total VAB days used" content={`${vabDaysUsed}`}/>
      <SingleDetailField title="Total unpaid leave days used" content={`${unpaidLeaveUsed}`}/>
    </div>
  );
};

export default AbsenceInfo;
