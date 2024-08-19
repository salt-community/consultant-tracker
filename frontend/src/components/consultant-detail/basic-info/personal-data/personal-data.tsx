import { TotalDaysStatisticsType } from "@/types";

type Props = {
  email: string;
  phone: string;
  client: string;
  totalDaysStatistics: TotalDaysStatisticsType;
};

const PersonalData = ({ email, phone, client, totalDaysStatistics }: Props) => {
  return (
    <div>
      <h3>Email: {email}</h3>
      <h3>Phone: {phone === null ? "-": phone}</h3>
      <h3>Client: {client}</h3>
      <hr />
      <div>
        <h3>Statistics</h3>
        <h4>Total Worked Days: {totalDaysStatistics.totalWorkedDays}</h4>
        <h4>Total Remaining Days: {totalDaysStatistics.totalRemainingDays}</h4>
        <h4>Total Worked Hours: {totalDaysStatistics.totalWorkedHours}</h4>
        <h4>Total Remaining Hours: {totalDaysStatistics.totalRemainingHours}</h4>
      </div>
    </div>
  );
};

export default PersonalData;
