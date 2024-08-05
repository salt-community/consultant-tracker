"use client"
import Timeline from "react-calendar-timeline";
import "react-calendar-timeline/lib/Timeline.css";
import moment from "moment";
import {ConsultantItemsType, ConsultantsCalendarType} from "@/types";
import "./gantt-chart.css";

type Props = {
  itemsProps: ConsultantItemsType[],
  groupsProps: ConsultantsCalendarType[]
}
const GanttChart = ({itemsProps, groupsProps}: Props) => {
  const handleItemClick =(itemId,e,time)=>{
    console.log(itemsProps.filter(el=>el.id == itemId));
  }
  const handleItemSelect =(itemId,e,time)=>{
    console.log(e.target)
  }
  const itemRenderer = ({
                    item,
                    itemContext,
                    getItemProps,
                  }) => {
    const backgroundColor = itemContext.selected ? "pink" : item.itemProps.style.background;
    const borderColor = itemContext.selected ? "pink" : item.itemProps.style.borderColor;
    return (
      <div
        {...getItemProps({
          style: {
            backgroundColor,
            color: item.color,
            borderColor,
            borderStyle: 'solid',
            borderWidth: 1,
            borderRadius: 4,
            borderLeftWidth: itemContext.selected ? 3 : 1,
            borderRightWidth: itemContext.selected ? 3 : 1,
          }
        }) }
      >
      </div>
    )
  }
  return (
    groupsProps.length > 0 && itemsProps.length > 0 &&
    <div>
        <div>
          <Timeline
              groups={groupsProps}
              items={itemsProps}
              itemTouchSendsClick={false}
              onItemSelect={handleItemSelect}
              itemRenderer={itemRenderer}
              canMove={false}
              onItemClick={(itemId, e, time)=>handleItemClick(itemId,e,time)}
              defaultTimeStart={moment().add(-17, "day")}
              defaultTimeEnd={moment().add(4, "day")}
          />
        </div>
    </div>
  );
};

export default GanttChart;
