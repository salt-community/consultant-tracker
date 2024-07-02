"use client";
import Infographic from "./infographic/infographic";
import "./dashboard.css";
import { infographicData } from "@/mockData";
import { ChangeEvent, useState } from "react";
import FilterField from "../filter/filter";

const Dashboard = () => {
  return (
    <section>
      <div className="dashboard-infographic__card">
        {infographicData.map((element, index) => {
          const { title, amount, variant } = element;
          return (
            <Infographic
              key={index}
              title={title}
              amount={amount}
              variant={variant}
            />
          );
        })}
      </div>
      <div className="dashboard-table__wrapper">
        <FilterField />
      </div>
    </section>
  );
};

export default Dashboard;
