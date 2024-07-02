"use client"
import Infographic from "./infographic/infographic";
import "./dashboard.css";
import { infographicData } from "@/mockData";
import { ChangeEvent, useState } from "react";

const Dashboard = () => {

  const [filterValue, setFilterValue] = useState("");

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFilterValue(e.target.value);
    console.log("filter: ", filterValue)
  }
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
      <input
        type="text"
        value={filterValue}
        onChange={(e) => handleInputChange(e)}
        placeholder='Type to filter by ...'
      />
      </div>
    </section>
  );
};

export default Dashboard;
