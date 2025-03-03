import "./personal-data.css";
import { useSelector } from "react-redux";
import toast, { Toaster } from "react-hot-toast";
import { SingleDetailField } from "../../../components";
import { RootState } from "../../../store/store";

export const PersonalData = () => {
  const personalData = useSelector(
    (state: RootState) => state.basicInfo.personalData
  );
  const { email, client, totalDaysStatistics, responsiblePt } = personalData!;
  const handleEmailCopy = () => {
    void navigator.clipboard.writeText(email);
    toast.success("Email copied");
  };
  return (
    <div className="personal-data__container">
      <div className="personal-data__info">
        <SingleDetailField
          title="Email @appliedtechnology.se"
          content={email.split("@")[0]}
          onClick={handleEmailCopy}
        />
        <SingleDetailField title="Client" content={client} />
        <SingleDetailField title="Responsible P&T" content={responsiblePt} />
      </div>
      <div className="personal-data__statistics">
        <div className="personal-data__statistics-wrapper">
          <div className="personal-data_statistics-days">
            <h4>Total Days</h4>
            <div className="personal-data_statistics__content">
              <SingleDetailField
                title="Worked"
                content={`${totalDaysStatistics.totalWorkedDays}`}
              />
              <SingleDetailField
                title="Remaining"
                content={`${totalDaysStatistics.totalRemainingDays}`}
              />
            </div>
          </div>
          <div className="personal-data_statistics-days">
            <h4>Total Hours</h4>
            <div className="personal-data_statistics__content">
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
      <Toaster />
    </div>
  );
};
