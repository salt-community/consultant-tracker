"use client";

import { consultantDetailsData } from "@/mockData";
import { ConsultantDetailsDataType } from "@/types";
import { usePathname } from "next/navigation";
import React, { useEffect, useState } from "react";
import { PiPencilSimpleLineThin } from "react-icons/pi";
import "../basic-info/basic-info.css"

const Client = () => {
  const idParam = usePathname().split("/").pop();
  const [clientData, setClientData] = useState<ConsultantDetailsDataType>(
    consultantDetailsData[0]
  );

  useEffect(() => {
    const consultantById = consultantDetailsData.filter(
      (el) => el.id == idParam
    );
    setClientData(consultantById[0]);
  }, [idParam]);

  return (
    clientData && (
      <div>
        {clientData.client.map((item) => {
          const { id, name, startDate, endDate } = item;
          return (
            <div key={id}>
              <div className="basic-info__contact-title">
                <h3>{name}</h3>
                <PiPencilSimpleLineThin />
              </div>
              <p>Start date : {startDate}</p>
              <p>End date: {endDate}</p>
            </div>
          );
        })}
      </div>
    )
  );
};

export default Client;
