import EditCard from "@/components/edit-card/edit-card";
import React, { useState } from "react";
import ContactDetailsForm from "./form/form";
import "./contact-details.css";

type Props = {
  email: string;
  phone: string;
  github: string;
};

const ContactDetails = ({ email, phone, github }: Props) => {
  const [readonly, setReadonly] = useState(true);

  const handleClick = (v: boolean) => {
    setReadonly(v);
  };

  return (
    <div className="contact-details-card">
      <EditCard
        title="Contact details"
        readonly={readonly}
        handleClick={handleClick}
      />
      <ContactDetailsForm email={email} phone={phone} github={github} readonly={readonly} />
    </div>
  );
};

export default ContactDetails;
