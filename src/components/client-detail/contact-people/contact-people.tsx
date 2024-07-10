"use client";
import { ContactPeopleType } from "@/types";
import "./contact-people.css";
import Box from "@mui/material/Box";
import TextField from "@/components/text-field/text-field";
import { useState } from "react";
import Edit from "@/components/edit/edit";
import MapBox from "./map-box/map-box";

type Props = {
  contactPeople: ContactPeopleType[];
};

const ContactPeople = ({ contactPeople }: Props) => {




  return (
    <>
      <h3>Contact People</h3>
      <div className="client-contact__container">
        {contactPeople.map((el, index) => (
          <MapBox
            index={index}
            name={el.name}
            phone={el.phone}
            email={el.email}
            id={el.id}
          />
        ))}
      </div>
    </>
  );
};

export default ContactPeople;
