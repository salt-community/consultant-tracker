"use client";

import { ConsultantDetailsDataType } from "@/types";
import "./basic-info.css";
import { consultantDetailsData } from "@/mockData";
import { useState } from "react";
import { usePathname } from "next/navigation";
import BasicInfoHeader from "@/components/consultant-detail/basic-info/header/header";
import ContactDetails from "./contact-details/contact-details";
import SaltContract from "./salt-contract/salt-contract";

const BasicInfo = () => {
  const id = usePathname().split("/").pop();
  const [personalData] = useState<ConsultantDetailsDataType>(
    consultantDetailsData.filter((c) => c.id === id)[0]
  );

  const { name, status, email, github, phone, startDate, remainingHours } =
    personalData;

  return (
    personalData && (
      <div className="basic-info__wrapper">
        <BasicInfoHeader name={name} status={status} />
        <div className="basic-info__data">
          <ContactDetails email={email} phone={phone} github={github} />
          <SaltContract startDate={startDate} remainingHours={remainingHours} />
        </div>
      </div>
    )
  );
};

export default BasicInfo;
