"use client";

import Indicator from "@/components/table/table-legend/indicator/indicator";
import {consultantDetailsData, consultantsData} from "@/mockData";
import {ConsultantDetailsDataType} from "@/types";
import {usePathname} from "next/navigation";
import React, {useEffect, useState} from "react";
import Breadcrumbs from "@/components/breadcrumbs/breadcrumbs";
import {useDetailsContext} from "@/context/details";

const PersonalData = () => {
  const idParam = usePathname().split("/").pop();
  const [personalData, setPersonalData] = useState<ConsultantDetailsDataType>(
    consultantDetailsData[0]
  );
  const details = useDetailsContext();
  const {status, name, email, phone, address, startDate, remainsHours} =
    personalData;
  useEffect(() => {
    const consultantById = consultantDetailsData.filter(
      (el) => el.id == idParam
    );
    setPersonalData(consultantById[0]);
    details.setName(personalData.name)
  }, [idParam, details, personalData.name]);

  return (
    personalData && (
      <>
        <section className="section-consultants">
          <h2>Personal Information</h2>
          <Indicator value={status}/>
          <p>Name: {name}</p>
          <p>Email: {email} </p>
          <p>Address: {address}</p>
          <p>Phone: {phone}</p>
          <p>Start date with Salt: {startDate}</p>
          <p>Remains hours with Salt: {remainsHours}</p>
        </section>
      </>
    )
  );
};

export default PersonalData;
