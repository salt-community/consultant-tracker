import { TotalDaysStatisticsType } from "@/types";
import SingleDetailField from "@/components/single-detail-field/single-detail-field";
import './personal-data.css'

type Props = {
  email: string;
  phone: string;
  client: string;
  totalDaysStatistics: TotalDaysStatisticsType;
};

const PersonalData = ({ email, phone, client, totalDaysStatistics }: Props) => {
  return (
    <div className="personal-data__container">
      <div className="personal-data__info">
      <SingleDetailField title="Email" content={email} />
      <SingleDetailField title="Phone" content={phone == null ? "-": phone}/>
      <SingleDetailField title="Client" content={client}/>
      </div>
      <div className="personal-data__statistics">
        <SingleDetailField title="Total worked days" content={`${totalDaysStatistics.totalWorkedDays}`}/>
        <SingleDetailField title="Total remaining days" content={`${totalDaysStatistics.totalRemainingDays}`}/>
        <SingleDetailField title="Total worked hours" content={`${totalDaysStatistics.totalWorkedHours}`}/>
        <SingleDetailField title="Total remaining hours" content={`${totalDaysStatistics.totalRemainingHours}`}/>
      </div>
    </div>
  );
};

export default PersonalData;
