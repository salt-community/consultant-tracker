"use client";
import { ConsultantDetailsDataType } from "@/types";
import "./basic-info.css";
import { consultantDetailsData } from "@/mockData";
import { useEffect, useState } from "react";
import Indicator from "@/components/table/table-legend/indicator/indicator";
import { usePathname } from "next/navigation";
import { useDetailsContext } from "@/context/details";
import { PiPencilSimpleLineThin } from "react-icons/pi";

const BasicInfo = () => {
  const image = "/avatar.svg";

  const id = usePathname().split("/").pop();
  const [personalData, setPersonalData] = useState<ConsultantDetailsDataType>(
    consultantDetailsData.filter((c) => c.id === id)[0]
  );
  const details = useDetailsContext();

  useEffect(() => {
    details.setName(name);
  }, []);
  const { name, status, email, github, phone, startDate, remainsHours } =
    personalData;
  return (
    personalData && (
      <div className="basic-info__wrapper">
        <div className="basic-info__highlight-name">
          <div className="basic-info__name">
            <h2>{name}</h2>
            <PiPencilSimpleLineThin />
          </div>
          <h5 className="basic-info__indicator">
            <Indicator value={status} /> {status}
          </h5>
        </div>
        <div className="basic-info__data">
          <div className="basic-info__card">
            <div className="basic-info__contact-title">
              <h3>Contact Details</h3>
              <PiPencilSimpleLineThin />
            </div>
            <p>Email: {email} </p>
            <p>Phone: {phone}</p>
            <p>GitHub: {github}</p>
          </div>
          <div className="basic-info__card">
            <div className="basic-info__contact-title">
              <h3>Salt Contract</h3>
              <PiPencilSimpleLineThin />
            </div>
            <p>Start date with Salt: {startDate}</p>
            <p>Remains hours with Salt: {remainsHours}</p>
          </div>
        </div>
      </div>
    )
  );
};

export default BasicInfo;
