"use client";

import Indicator from "@/components/table/table-legend/indicator/indicator";
import { consultantDetailsData, consultantsData } from "@/mockData";
import { ConsultantDetailsDataType } from "@/types";
import { usePathname } from "next/navigation";
import React, { useEffect, useState } from "react";

const PersonalData = () => {
  const idParam = usePathname().split("/").pop();
  const [personalData, setPersonalData] = useState<ConsultantDetailsDataType>(
    consultantDetailsData[0]
  );

  const { status, name, email, phone, address, startDate, remainsHours } =
    personalData;
  useEffect(() => {
    const consultantById = consultantDetailsData.filter(
      (el) => el.id == idParam
    );
    setPersonalData(consultantById[0]);
  }, [idParam]);

  return (
    personalData && (
      <div>
        <h2>Personal Information</h2>
        <Indicator value={status} />
        <p>Name: {name}</p>
        <p>Email: {email} </p>
        <p>Address: {address}</p>
        <p>Phone: {phone}</p>
        <p>Start date with Salt: {startDate}</p>
        <p>Remains hours with Salt: {remainsHours}</p>
      </div>
    )
  );
};

export default PersonalData;
