"use client";
import { ConsultantItemsType } from "@/types";
import { selectColor} from "@/utils/utils";
import React, { Dispatch, SetStateAction, useEffect } from "react";
import "./time-item-details.css";
import SingleDetailField from "@/components/single-detail-field/single-detail-field";

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
          <SingleDetailField title="Client" content={content.details.client}/>
          <SingleDetailField title="Total Days Selected" content={`${content.details.totalDays}`}/>
          <SingleDetailField title="Start Date" content={content.start_time.format("ddd, DD-MMM-YY")}/>
          <SingleDetailField title="End Date" content={content.end_time.format("ddd, DD-MMM-YY")}/>
        </>
      )}
    </div>
  );
}

export default TimeItemDetails;
