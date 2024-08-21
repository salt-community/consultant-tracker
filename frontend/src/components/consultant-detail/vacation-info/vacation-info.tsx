import "./vacation-info.css";

type Props = {
  vacationDaysUsed: number;
  sickDaysUsed?: number;
  parentalLeaveUsed?: number;
  vabDaysUsed?: number;
  unpaidLeaveUsed?: number
};

const VacationInfo = ({vacationDaysUsed, sickDaysUsed, parentalLeaveUsed, vabDaysUsed, unpaidLeaveUsed}: Props) => {
  return (
    <div className="vacation-info__wrapper">
      {/* <p>Available vacation days: 10</p>
      //fetch from Lucca ?  */}
      <p>Total Vacation days used: {vacationDaysUsed}</p>
      {/* //fetch current year from Lucca ?  */}
      {sickDaysUsed && <p>Total Sick days used: {sickDaysUsed}</p>}
      {parentalLeaveUsed && <p>Total Parental leave days used: {parentalLeaveUsed}</p>}
      {vabDaysUsed && <p>Total VAB days used: {vabDaysUsed}</p>}
      {unpaidLeaveUsed && <p>Total Unpaid leave days used: {unpaidLeaveUsed}</p>}
    </div>
  );
};

export default VacationInfo;
