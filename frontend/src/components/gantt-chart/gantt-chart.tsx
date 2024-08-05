"use client"
import Timeline from "react-calendar-timeline";
import "react-calendar-timeline/lib/Timeline.css";
import moment from "moment";
import {ConsultantItemsType, ConsultantsCalendarType} from "@/types";
import "./gantt-chart.css";
import {useEffect, useState} from "react";

type Props = {
  itemsProps: ConsultantItemsType[],
  groupsProps: ConsultantsCalendarType[]
}
const GanttChart = ({itemsProps, groupsProps}: Props) => {
  const handleItemClick =(itemId,e,time)=>{
    console.log('test')
    // e.target.style.borderColor = "red"
    // e.target.style.borderWidth = '5px'
    // e.target.style.borderStyle = 'solid'
    console.log(itemsProps.filter(el=>el.id == itemId));
  }
  const handleItemSelect =(itemId,e,time)=>{
    itemsProps.forEach(el=>{
      if(el.id !== itemId){
        if (el.itemProps && "border" in el.itemProps.style) {
            el.itemProps.style.borderWidth = "0"
        }
      }
    })
    e.target.itemProps.style.borderColor = "red"
    e.target.itemProps.style.borderWidth = '5px'
    e.target.itemProps.style.borderStyle = 'solid'
    console.log(itemsProps.filter(el=>el.id == itemId));
  }
  useEffect(() => {
  }, [itemsProps]);
  return (
    groupsProps.length > 0 && itemsProps.length > 0 &&
    <div>
        <div>
          <Timeline
              groups={groupsProps}
              items={itemsProps}
              itemTouchSendsClick={false}
              onItemSelect={handleItemSelect}
              // canMove={false}
              onItemClick={(itemId, e, time)=>handleItemClick(itemId,e,time)}
              defaultTimeStart={moment().add(-17, "day")}
              defaultTimeEnd={moment().add(4, "day")}
          />
        </div>
    </div>
  );
};

export default GanttChart;
