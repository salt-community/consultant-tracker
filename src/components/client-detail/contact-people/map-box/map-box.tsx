import Edit from "@/components/edit/edit";
import TextField from "@/components/text-field/text-field";
import Box from "@mui/material/Box";
import React, { useState } from "react";

type Props = {
  index: number;
  handleChange: () => void;
  name: string;
  phone: string;
  email: string;
};

const MapBox = ({ index, handleChange, name, phone, email }: Props) => {
  const [readonly, setReadonly] = useState<boolean>(true);
  const handleClick = (v: boolean) => {
    setReadonly(v);
  };
  return (
    <div key={index} className="client-contact-details-card">
      <Box
        component="form"
        className="client-contact-details-form"
        noValidate
        autoComplete="off"
        // onSubmit={(e) => onSubmit(e)}
      >
        <div className="card-edit">
          <Edit readonly={readonly} handleClick={handleClick} />
        </div>
        <TextField
          label="Name"
          onChange={handleChange}
          value={name}
          readonly={readonly}
          name="name"
        />
        <TextField
          label="Phone"
          onChange={handleChange}
          value={phone}
          readonly={readonly}
          name="phone"
        />
        <TextField
          label="Email"
          onChange={handleChange}
          value={email}
          readonly={readonly}
          name="email"
        />
      </Box>
    </div>
  );
};

export default MapBox;
