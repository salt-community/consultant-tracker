import * as Mui from "@mui/material";
import React, { ChangeEvent } from "react";

type Props = {
  label: string;
  value: string | number;
  readonly: boolean;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
};

const TextField = ({ label, value, readonly, onChange }: Props) => {
  return (
    <Mui.TextField
      id="outlined-basic"
      label={label}
      variant="standard"
      value={value}
      onChange={onChange}
      disabled={readonly}
    />
  );
};

export default TextField;
