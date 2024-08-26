"use client";

import { ClientDataType, RegisteredTimeItemType } from "@/types";
import { useEffect, useState } from "react";
import "../basic-info/basic-info.css";

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
            <div key={name}>
              <div className="basic-info__contact-title">
                <h3>{name}</h3>
                <p>Start Date: {startDate}</p>
                <p>End Date: {endDate}</p>
              </div>
              {/* <p>Start date : {startDate}</p>
              <p>End date: {endDate}</p> */}
            </div>
          );
        })}
      </div>
    )
  );
};

export default Client;
