"use client";
import { ConsultantDetailsDataType } from "@/types";
import "./basic-info.css";
import { consultantDetailsData } from "@/mockData";
import { useEffect, useState } from "react";
import Indicator from "@/components/table/table-legend/indicator/indicator";
import { usePathname } from "next/navigation";
const BasicInfo = () => {
  const image = "/avatar.svg";

  const id = usePathname().split("/").pop();
  const [personalData, setPersonalData] =
    useState<ConsultantDetailsDataType>(consultantDetailsData.filter((c) => c.id === id)[0]);
  const {name, status, email, github: address, phone, startDate, remainsHours} = personalData;
  return (
    personalData && (
      <aside className="basic-info-section">
        <div
          className="avatar"
          style={{ backgroundImage: `url(${image})`, backgroundSize: "cover" }}
        />
        <div className="basic-info__highlight-name">
          <h3>{name}</h3>
          <h5 className="basic-info__indicator">
            <Indicator value={status} /> {status}
          </h5>
        </div>
        <section className="section-consultants">
          <p>Email: {email} </p>
          <p>Phone: {phone}</p>
          <p>GitHub: {address}</p>
          <p>Start date with Salt: {startDate}</p>
          <p>Remains hours with Salt: {remainsHours}</p>
        </section>
      </aside>
    )
  );
};

export default BasicInfo;
