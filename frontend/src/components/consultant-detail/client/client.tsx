"use client";

import { ClientDataType, RegisteredTimeItemType } from "@/types";
import { useEffect, useState } from "react";
import "./client.css";
import SingleDetailField from "@/components/single-detail-field/single-detail-field";

type Props = {
  clientList: ClientDataType[];
};

const Client = ({ clientList }: Props) => {
  const [filteredData, setFilteredData] = useState<RegisteredTimeItemType[]>(
    []
  );
 console.log(clientList);
  // const filterData = () => {
  //   const filter = new Set();
  //   // console.log(registeredTime);
  //   registeredTime.forEach((el)=>{filter.add(el.projectName)});
  // };

  // useEffect(() => {
  //   filterData();
  // }, []);

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
