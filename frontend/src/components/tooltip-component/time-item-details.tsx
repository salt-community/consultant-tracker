"use client";
import { ConsultantItemsType } from "@/types";
import { selectColor, workingDays } from "@/utils/utils";
import React, { Dispatch, SetStateAction, useEffect } from "react";
import "./time-item-details.css";

type Props = {
  setOpenTooltip: Dispatch<SetStateAction<boolean>>;
  openTooltip: boolean;
  content: ConsultantItemsType;
};

function TimeItemDetails({ setOpenTooltip, openTooltip, content }: Props) {
  useEffect(() => {
  }, [content]);
  let borderClassName = selectColor(content.title!.toString());
  return (
    <div
      className="time-details"
      style={{ border: `2px solid ${borderClassName}` }}
    >
      {content && (
        <>
          <h3>{content.title}</h3>
          <hr />
          <div className="time-details__wrapper">
            <p className="details__title">Client:</p>
            <p>{content.details.client}</p>
          </div>
          <div className="time-details__wrapper">
            <p className="details__title">Total Days Selected: </p>
            <p>{workingDays(content.start_time, content.end_time)}</p>
          </div>
          <div className="time-details__wrapper">
            <p className="details__title">Start Date:</p>
            <p>{content.start_time.format("ddd, DD-MMM-YY")}</p>
          </div>
          <div className="time-details__wrapper">
            <p className="details__title">End Date:</p>
            <p>{content.end_time.format("ddd, DD-MMM-YY")}</p>
          </div>
        </>
      )}
    </div>
  );
}

export default TimeItemDetails;
