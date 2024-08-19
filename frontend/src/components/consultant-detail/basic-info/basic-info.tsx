"use client";

import { ConsultantDetailsDataType } from "@/types";
import "./basic-info.css";
import { useEffect, useState } from "react";
import { usePathname } from "next/navigation";
import BasicInfoHeader from "@/components/consultant-detail/basic-info/header/header";
import { getConsultantById } from "@/api";

type Props = {
  id: string;
};

const BasicInfo = ({ id }: Props) => {
  // const id = usePathname().split("/").pop();
  console.log("id basicInfo",id);
  const [personalData, setPersonalData] = useState<ConsultantDetailsDataType>();
  const fetchConsultantById = () => {
    if (id) getConsultantById(id).then((res) => setPersonalData(res));
  };
  useEffect(() => {
    fetchConsultantById();
  }, [id]);

  return (
    personalData && (
      <div className="basic-info__wrapper">
        <BasicInfoHeader name={personalData.fullName} />
        {/*<div className="basic-info__data">*/}
        {/*  <ContactDetails email={email} phone={phone} github={github} />*/}
        {/*  <SaltContract startDate={startDate} remainingHours={remainingHours} />*/}
        {/*</div>*/}
      </div>
    )
  );
};

export default BasicInfo;
