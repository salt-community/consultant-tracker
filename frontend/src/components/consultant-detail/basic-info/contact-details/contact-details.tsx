import { Box } from "@mui/material";
import React, { ChangeEvent, FormEvent, useState } from "react";
import "./contact-details.css";
import TextField from "@/components/text-field/text-field";
import { usePathname } from "next/navigation";
import { ConsultantDetailsDataType } from "@/types";
import { useDetailsContext } from "@/context/details";
import Edit from "@/components/edit/edit";

type Props = {
  email: string;
  phone: string;
  github: string;
};

const ContactDetails = ({ email, phone, github }: Props) => {
  const [contactData, setContactData] = useState({
    email,
    phone,
    github,
  });
  const [readonly, setReadonly] = useState(true);
  const idParam = usePathname().split("/").pop();
  const details = useDetailsContext();

  const onSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    details.setData(
      details.data.map((el: ConsultantDetailsDataType) => {
        if (el.id === idParam) {
          el.email = contactData.email;
          el.phone = contactData.phone;
          el.github = contactData.github;
        }
        return el;
      })
    );
    setReadonly(true);
  };

  const filterData = () => {
    return details.data.filter((el) => el.id === idParam)[0];
  };
  const handleClick = (v: boolean) => {
    setReadonly(v);
    if (v) {
      const filteredData = filterData();
      const data = {
        email: filteredData.email,
        phone: filteredData.phone,
        github: filteredData.github,
      };
      setContactData(data);
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setContactData({
      ...contactData,
      [name]: value,
    });
  };

  return (
    <div className="contact-details-card">
      <Box
        component="form"
        className="contact-details-form"
        noValidate
        autoComplete="off"
        onSubmit={(e) => onSubmit(e)}
      >
        <div className="edit-card__container">
          <h2>Contact Details</h2>
          <Edit readonly={readonly} handleClick={handleClick} />
        </div>
        <TextField
          label="Email"
          onChange={handleChange}
          value={contactData.email}
          readonly={readonly}
          name="email"
        />
        <TextField
          label="Phone"
          onChange={handleChange}
          value={contactData.phone}
          readonly={readonly}
          name="phone"
        />
        <TextField
          label="GitHub"
          onChange={handleChange}
          value={contactData.github}
          readonly={readonly}
          name="github"
        />
      </Box>
    </div>
  );
};

export default ContactDetails;
