"use client";
import "./client.css"
import SingleDetailField from "@/components/single-detail-field/single-detail-field";
import {useSelector} from "react-redux";
import {RootState} from "@/store/store";


const Client = () => {
  const clientList = useSelector((state: RootState)=> state.basicInfo.personalData.clientsList)
  return (
    clientList && (
      <div>
        {clientList.map((item) => {
          const { name, startDate, endDate } = item;
          return (
            <div key={name} className="basic-info__client-wrapper">
                <h3 className="basic-info__client-title">{name}</h3>
                <SingleDetailField title="Start Date" content={startDate}/>
                <SingleDetailField title="End Date" content={endDate}/>
            </div>
          );
        })}
      </div>
    )
  );
};

export default Client;
