"use client";
import { ConsultantItemsType } from '@/types';
import { selectColor, workingDays } from '@/utils/utils';
import React, { Dispatch, SetStateAction, useEffect } from 'react'
import './time-item-details.css'

type Props = {
    setOpenTooltip: Dispatch<SetStateAction<boolean>>;
    openTooltip: boolean
    content: ConsultantItemsType;
}

function TimeItemDetails({setOpenTooltip, openTooltip, content}: Props) {
    useEffect(() => {

    },[content])
    console.log("content: ",content);
    let borderClassName = selectColor(content.title!.toString());
  return (
    <div className="item-details" style={{border:`1px solid ${borderClassName}`}}>
        <h3>Selected Time bar:</h3>
        { content &&
          <>
          <h3>{content.details.name}</h3>
          <p>{content.title}</p>
          <p>Total Days Selected: {workingDays(content.start_time, content.end_time)}</p>
          <p>Start Date: {content.start_time.format("ddd, DD-MMM-YY")}</p>
          <p>End Date: {content.end_time.format("ddd, DD-MMM-YY")}</p>
          </>}
        </div>
  )
}

export default TimeItemDetails;