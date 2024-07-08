"use client";
import {ConsultantDetailsDataType} from "@/types";
import "./basic-info.css";
import {consultantDetailsData} from "@/mockData";
import {FormEvent, useEffect, useRef, useState} from "react";
import Indicator from "@/components/table/table-legend/indicator/indicator";
import {usePathname} from "next/navigation";
import {useDetailsContext} from "@/context/details";
import {PiPencilSimpleLineThin} from "react-icons/pi";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";

const BasicInfo = () => {
    const image = "/avatar.svg";

    const id = usePathname().split("/").pop();
    const [personalData] = useState<ConsultantDetailsDataType>(
        consultantDetailsData.filter((c) => c.id === id)[0]
    );
    const details = useDetailsContext();
    let refName = useRef<HTMLInputElement>(null);
    const [nameReadOnly, setNameReadOnly] = useState(true);

    const handleClick = () => {
        console.log("CLICKED");
        setNameReadOnly(false);
    };

    const changeName = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        console.log(refName.current?.value)
    }

    const {name, status, email, github, phone, startDate, remainsHours} =
        personalData;
    useEffect(() => {
        details.setName(name);
    }, [details, name]);

    const FONT_SIZE = 9
    const DEFAULT_INPUT_WIDTH = 200

    const [textValue, setTextValue] = useState("")
    const [inputWidth, setInputWidth] = useState(DEFAULT_INPUT_WIDTH)
    useEffect(() => {
        if (textValue.length * FONT_SIZE > DEFAULT_INPUT_WIDTH) {
            setInputWidth((textValue.length + 1) * FONT_SIZE)
            setTextValue(name)
        } else {
            setInputWidth(DEFAULT_INPUT_WIDTH)
            setTextValue(name)
        }
    }, [name, textValue])
    return (
        personalData && (
            <div className="basic-info__wrapper">
                <div className="basic-info__highlight-name">
                    <aside className="basic-info-section">
                        <div
                            className="avatar"
                            style={{backgroundImage: `url(${image})`}}
                        />
                    </aside>
                    <div className="basic-info__title">
                        <div className="basic-info__name">
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
                                onSubmit={(e) => changeName(e)}
                            >
                                <TextField
                                    id="outlined-basic"
                                    label=""
                                    variant="standard"
                                    defaultValue={name}
                                    InputProps={{
                                        readOnly: nameReadOnly,
                                        style: {width: `${inputWidth}px`}
                                    }}
                                    disabled={nameReadOnly}
                                    inputRef={refName}
                                />
                            </Box>
                            <PiPencilSimpleLineThin onClick={handleClick}/>
                        </div>
                        <h5 className="basic-info__indicator">
                            <Indicator value={status}/> {status}
                        </h5>
                    </div>
                </div>
                <div className="basic-info__data">
                    <div className="basic-info__card">
                        <div className="basic-info__contact-title">
                            <h3>Contact Details</h3>
                            <PiPencilSimpleLineThin/>
                        </div>
                        <Box
                            component="form"
                            sx={{
                                // "& > :not(style)": {
                                width: "100%",
                                display: "flex",
                                flexDirection: "column",
                                gap: "15px",
                                color: "black",
                                // },
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
