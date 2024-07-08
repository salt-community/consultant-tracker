"use client";
import Client from "@/components/consultant-detail/client/client";
import Schedule from "@/components/consultant-detail/schedule/schedule";
import "./page.css";
import React, { SyntheticEvent, useState } from "react";
import VacationInfo from "@/components/consultant-detail/vacation-info/vacation-info";
import TabsComponent from "@/components/tabs/tabs";
import { useDetailsContext } from "@/context/details";
import BasicInfo from "@/components/consultant-detail/basic-info/basic-info";
import BreadcrumbsComponent from "@/components/breadcrumbs/breadcrumbs";

const ConsultantDetail = () => {
  const [value, setValue] = useState("clients");

  const handleChange = (event: SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };
  const details = useDetailsContext();
  const content = () => {
    switch (value) {
      case "schedule":
        return <Schedule />;
      case "vacation":
        return <VacationInfo />;
      default:
        return <Client />;
    }
  };
  return (
    <>
      <BreadcrumbsComponent current={`${details.name}`} />
      <div className="detail-page__wrapper">
        <section className="section-consultants">
          <div className="detail-page__right-side__wrapper">
            <BasicInfo />
            <div className="detail-page__tabs-wrapper">
              <TabsComponent value={value} handleChange={handleChange} />
              <div className="consultant-detail__card">{content()}</div>
            </div>
          </div>
        </section>
      </div>
    </>
  );
};

export default ConsultantDetail;
