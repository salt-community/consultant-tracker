import {FormEvent, useEffect, useRef, useState} from "react";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import {useDetailsContext} from "@/context/details";
import "./name.css";
import {usePathname} from "next/navigation";
import {FONT_SIZE} from "@/constants";
import {consultantDetailsData} from "@/mockData";
import Edit from "@/components/edit/edit";

type Props = {
  name: string;
};

const HeaderName = ({name}: Props) => {
  const [nameReadonly, setNameReadonly] = useState(true);
  const [inputName, setInputName] = useState(name);
  const refName = useRef<HTMLInputElement | null>(null);
  const idParam = usePathname().split("/").pop();
  const details = useDetailsContext();

  const handleClick = (v: boolean) => {
    setNameReadonly(v);
    setInputName(details.name);
  };

  const changeName = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    details.setData(details.data.map((el) => {
      if (el.id === idParam) {
        el.name = refName.current!.value;
      }
      return el
    }));
    setInputName(refName.current!.value);
    details.setName(refName.current!.value);
    setNameReadonly(true);
  };

  useEffect(() => {
    if (refName.current!.value === name) {
      details.setName(name);
    }
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

export default HeaderName;
