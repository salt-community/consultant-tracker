import {TextField} from "@mui/material";
import { ChangeEvent } from "react";
import './text-input.css'

type Props = {
  label: string;
  value: string | number;
  name: string,
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
};

export const TextInput = ({ label, value, onChange, name }: Props) => {
  return (
    <TextField
      id="outlined-basic"
      label={label}
      variant="outlined"
      value={value}
      onChange={onChange}
      name={name}
    />
  );
};

