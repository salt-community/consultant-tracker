"use client"
import React, {FormEvent, useEffect, useRef, useState} from 'react';
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import {FONT_SIZE} from "@/constants";
import Edit from "@/components/edit/edit";
import {usePathname} from "next/navigation";
import {useClientsContext} from "@/context/clients";
import "./header.css"

type Props = {
  name: string
}
const Header = ({name}: Props) => {
  const [nameReadonly, setNameReadonly] = useState(true);
  const [inputName, setInputName] = useState(name);
  const refName = useRef<HTMLInputElement | null>(null);
  const idParam = usePathname().split("/").pop();
  const client = useClientsContext();

  const handleClick = (v: boolean) => {
    setNameReadonly(v);
    setInputName(client.name);
  };

  const changeName = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    client.setData(client.data.map((el) => {
      if (el.id === idParam) {
        el.name = refName.current!.value;
      }
      return el;
    }));
    setInputName(refName.current!.value);
    client.setName(refName.current!.value);
    setNameReadonly(true);
  };

  useEffect(() => {
    if (refName.current!.value === name) {
      client.setName(name);
    }
  }, [client, name]);
  return (
    <div>
      <Box
        component="form"
        className="header-name__form"
        noValidate
        autoComplete="off"
        onSubmit={(e) => changeName(e)}
      >
        <TextField
          id="outlined-basic"
          variant="standard"
          value={inputName}
          onChange={(e) => setInputName(e.target.value)}
          InputProps={{
            style: {
              width: `${inputName.length * FONT_SIZE}px`,
              fontSize: "25px",
              fontWeight: 600,
            },
          }}
          disabled={nameReadonly}
          inputRef={refName}
        />
        <Edit readonly={nameReadonly} handleClick={handleClick}/>
      </Box>
    </div>
  );
};

export default Header;