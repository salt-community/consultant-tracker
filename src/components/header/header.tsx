"use client"
import React, {FormEvent, useContext, useEffect, useRef, useState} from 'react';
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import {FONT_SIZE} from "@/constants";
import Edit from "@/components/edit/edit";
import {usePathname} from "next/navigation";
import {ClientsContext, ClientsContextType} from "@/context/clients";
import {DetailsContext, DetailsContextType} from "@/context/details";
import { ConsultantDetailsDataType, ClientsDetailsDataType } from "@/types";

type Props = {
  name: string,
  variant: string
}
const Header = ({name, variant}: Props) => {
  const [nameReadonly, setNameReadonly] = useState(true);
  const [inputName, setInputName] = useState(name);
  const refName = useRef<HTMLInputElement | null>(null);
  const idParam = usePathname().split("/").pop();
  const selectContext =  variant === "client" ? ClientsContext : DetailsContext;
  const context =  useContext<ClientsContextType |  DetailsContextType>(selectContext);

  const handleClick = (v: boolean) => {
    setNameReadonly(v);
    setInputName(context.name);
  };

  const changeName = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const mappedData = context.data.map((el) => {
      if (el.id === idParam) {
        el.name = refName.current!.value;
      }
      return el;
    })
      context.setData(mappedData);
      context.setName(refName.current!.value);
    setInputName(refName.current!.value);
    setNameReadonly(true);
  };

  useEffect(() => {
    if (refName.current!.value === name) {
      if(variant === "client"){
        context.setName(name);
      }
      else{
        context.setName(name);
      }
    }
  }, [context, variant, name]);
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