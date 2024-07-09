import { Box } from "@mui/material";
import React, { ChangeEvent, useState } from "react";
import "./form.css";
import TextField from "@/components/text-field/text-field";

type Props = {
  email: string;
  phone: string;
  github: string;
  readonly: boolean
};

const ContactDetailsForm = ({ email, phone, github, readonly }: Props) => {
  const [contactData, setContactData] = useState({
    email,
    phone,
    github,
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setContactData({
      ...contactData,
      [name]: value,
    });
  };

  return (
    <>
      <Box
        component="form"
        className="contact-details-form"
        noValidate
        autoComplete="off"
      >
        <TextField
          label="Email"
          onChange={handleChange}
          value={email}
          readonly={readonly}
        />
        <TextField
          label="Phone"
          onChange={handleChange}
          value={phone}
          readonly={readonly}
        />
        <TextField
          label="GitHub"
          onChange={handleChange}
          value={github}
          readonly={readonly}
        />
      </Box>
    </>
  );
};

export default ContactDetailsForm;
