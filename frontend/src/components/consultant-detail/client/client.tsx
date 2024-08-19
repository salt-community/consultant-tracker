"use client";

import { RegisteredTimeItemType } from "@/types";
import { useEffect, useState } from "react";
import "../basic-info/basic-info.css";

type Props = {
  registeredTime: RegisteredTimeItemType[];
};

const Client = ({ registeredTime }: Props) => {
  const [filteredData, setFilteredData] = useState<RegisteredTimeItemType[]>(
    []
  );

  const filterData = () => {
    const filter = new Set();
    registeredTime.forEach((el)=>{filter.add(el.projectName)});
    console.log("filter",filter);
  };

  useEffect(() => {
    filterData();
  }, []);

  return (
    registeredTime && (
      <div>
        {registeredTime.map((item) => {
          const { projectName } = item;
          return (
            <div key={projectName}>
              <div className="basic-info__contact-title">
                <h3>{projectName}</h3>
              </div>
              {/* <p>Start date : {strtDate}</p>
              <p>End date: {endDate}</p> */}
            </div>
          );
        })}
      </div>
    )
  );
};

export default Client;
