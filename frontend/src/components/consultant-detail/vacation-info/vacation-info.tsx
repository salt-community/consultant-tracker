import "./vacation-info.css";

type Props = {
  vacationDaysUsed: number;
};

const VacationInfo = ({vacationDaysUsed}: Props) => {
  return (
    <div className="vacation-info__wrapper">
      <p>Available vacation days: 10</p>
      //fetch from Lucca ? 
      <p>Vacation days used in current year: {vacationDaysUsed}</p>
      //fetch current year from Lucca ? 
    </div>
  );
};

export default VacationInfo;
