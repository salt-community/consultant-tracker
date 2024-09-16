import * as Mui from "@mui/material";
import { ChangeEvent } from "react";

type Props = {
  label: string;
  value: string | number;
  readonly: boolean;
  name: string,
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
};

const TextField = ({ label, value, readonly, onChange, name }: Props) => {
  return (
    <Mui.TextField
      id="outlined-basic"
      label={label}
      variant="standard"
      value={value}
      onChange={onChange}
      disabled={readonly}
      name={name}
    />
  );
};

export default TextField;
