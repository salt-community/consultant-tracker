"use client";
import {ConsultantDetailsDataType} from "@/types";
import "./basic-info.css";
import {consultantDetailsData} from "@/mockData";
import {useState} from "react";
import {usePathname} from "next/navigation";
import {PiPencilSimpleLineThin} from "react-icons/pi";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import BasicInfoHeader from "@/components/consultant-detail/basic-info/basic-info-header/basic-info-header";

const BasicInfo = () => {

  const id = usePathname().split("/").pop();
  const [personalData] = useState<ConsultantDetailsDataType>(
    consultantDetailsData.filter((c) => c.id === id)[0]
  );
  const {name, status, email, github, phone, startDate, remainsHours} =
    personalData;
  return (
    personalData && (
      <div className="basic-info__wrapper">
        <BasicInfoHeader name={name} status={status}/>
        <div className="basic-info__data">
          <div className="basic-info__card">
            <div className="basic-info__contact-title">
              <h3>Contact Details</h3>
              <PiPencilSimpleLineThin/>
            </div>
            <Box
              component="form"
              sx={{
                width: "100%",
                display: "flex",
                flexDirection: "column",
                gap: "15px",
                color: "black",
              }}
              noValidate
              autoComplete="off"
            >
              <TextField
                id="outlined-basic"
                label="Email"
                variant="standard"
                value={email}
                InputProps={{
                  readOnly: true,
                }}
                disabled
              />
              <TextField
                id="outlined-basic"
                label="Phone"
                variant="standard"
                value={phone}
                InputProps={{
                  readOnly: true,
                }}
                disabled
              />
              <TextField
                id="outlined-basic"
                label="GitHub"
                variant="standard"
                value={github}
                InputProps={{
                  readOnly: true,
                }}
                disabled
              />
            </Box>
          </div>
          <div className="basic-info__card">
            <div className="basic-info__contact-title">
              <h3>Salt Contract</h3>
              <PiPencilSimpleLineThin/>
            </div>
            <p>Start date with Salt: {startDate}</p>
            <p>Remains hours with Salt: {remainsHours}</p>
          </div>
        </div>
      </div>
    )
  );
};

export default BasicInfo;
