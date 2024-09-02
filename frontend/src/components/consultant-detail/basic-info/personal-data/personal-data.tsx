import SingleDetailField from "@/components/single-detail-field/single-detail-field";
import "./personal-data.css";
import {useSelector} from "react-redux";
import {RootState} from "@/store/store";


const PersonalData = () => {
  const personalData = useSelector((state: RootState)=> state.basicInfo.personalData)
  const {email, client, totalDaysStatistics} = personalData;
  return (
    <div className="personal-data__container">
      <div className="personal-data__info">
        <SingleDetailField title="Email" content={email} />
        <SingleDetailField title="Client" content={client} />
      </div>
      <div className="personal-data__statistics">
        <div className="personal-data__statistics-wrapper">
          <div className="personal-data_statistics-days">
            <h4>Total Days</h4>
            <SingleDetailField
              title="Worked"
              content={`${totalDaysStatistics.totalWorkedDays}`}
            />
            <SingleDetailField
              title="Remaining"
              content={`${totalDaysStatistics.totalRemainingDays}`}
            />
          </div>
          <div className="personal-data_statistics-days">
            <h4>Total Hours</h4>
            <SingleDetailField
              title="Worked"
              content={`${totalDaysStatistics.totalWorkedHours}`}
            />
            <SingleDetailField
              title="Remaining"
              content={`${totalDaysStatistics.totalRemainingHours}`}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default PersonalData;
