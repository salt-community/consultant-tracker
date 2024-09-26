import { RootState } from "../../../store/store";
import {SingleDetailField} from "../../../components";
import "./client.css"
import {useSelector} from "react-redux";

export const Client = () => {
  const clientList = useSelector((state: RootState)=> state.basicInfo.personalData!.clientsList)
  return (
    clientList && (
      <div>
        {clientList.map((item) => {
          const { name, startDate, endDate } = item;
          return (
            <div key={name} className="basic-info__client-wrapper">
                <h4 className="basic-info__client-title">{name}</h4>
                <SingleDetailField title="Start Date" content={startDate}/>
                <SingleDetailField title="End Date" content={endDate}/>
            </div>
          );
        })}
      </div>
    )
  );
};
