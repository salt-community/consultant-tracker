import React, {FormEvent, useEffect, useRef, useState} from 'react';
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import {PiPencilSimpleLineThin} from "react-icons/pi";
import {useDetailsContext} from "@/context/details";
import './header-name.css'
import {usePathname} from "next/navigation";
import {FONT_SIZE} from "@/constants";
import {consultantDetailsData} from "@/mockData";
import Button from "@mui/material/Button";

type Props = {
  name: string
}

const HeaderName = ({name}: Props) => {
  const [nameReadOnly, setNameReadOnly] = useState(true);
  const [nameLength, setNameLength] = useState(name.length);

  const refName = useRef<HTMLInputElement | null>(null);
  const idParam = usePathname().split("/").pop();
  const details = useDetailsContext();
  const handleClick = () => {
    setNameReadOnly(false);
  };

  const changeName = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    consultantDetailsData.forEach(el => {
      if (el.id === idParam) {
        el.name = refName.current!.value;
      }
    })
    setNameLength(refName.current!.value.length)
    setNameReadOnly(true);
  }

  useEffect(() => {
    details.setName(name);
  }, [details, name]);


  return (
    <div className="header-name__container">
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
          defaultValue={name}
          InputProps={{
            style: {width: `${nameLength * FONT_SIZE}px`, fontSize: "25px", fontWeight: 600}
          }}
          disabled={nameReadOnly}
          inputRef={refName}
        />
        {nameReadOnly
          ? <PiPencilSimpleLineThin onClick={handleClick} className="header-name__edit"/>
          : <Button type="submit" variant="text">Submit</Button>}
      </Box>
    </div>
  );
};

export default HeaderName;