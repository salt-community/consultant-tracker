"use client"
import Timeline from "react-calendar-timeline";
import "react-calendar-timeline/lib/Timeline.css";
import moment from "moment";
import {ConsultantItemsType, ConsultantsCalendarType} from "@/types";
import "./gantt-chart.css";
import {selectColor} from "@/utils/utils";
import {useEffect, useState} from "react";
import Tooltip from '../tooltip/tooltip'

type Props = {
  itemsProps: ConsultantItemsType[],
  groupsProps: ConsultantsCalendarType[]
}
const GanttChart = ({itemsProps, groupsProps}: Props) => {
  const [modalData, setModalData] = useState<ConsultantItemsType>();


  const handleItemSelect = (itemId, e, time) => {
    const consultantItems = itemsProps.filter(el => itemId == el.id)[0];
    setModalData(consultantItems);
  }
  useEffect(() => {
  }, [modalData]);
  const itemRenderer = ({
                          item,
                          itemContext,
                          getItemProps,
                        }) => {
    const chosenColor = selectColor(item.title)
    const background = itemContext.selected ? chosenColor : item.itemProps.style.background;
    const borderColor = itemContext.selected ? "black" : item.itemProps.style.borderColor;
    return (
          <div
            {...getItemProps({
              style: {
                background,
                color: item.color,
                borderColor,
                borderStyle: 'solid',
                borderWidth: 1,
                borderRadius: 4,
                borderLeftWidth: itemContext.selected ? 3 : 1,
                borderRightWidth: itemContext.selected ? 3 : 1,
              }
            })}
          />
    )
  }
  return (
    groupsProps.length > 0 && itemsProps.length > 0 &&
    <div>
        <div>
            <Timeline
                groups={groupsProps}
                items={itemsProps}
                onItemSelect={handleItemSelect}
                itemRenderer={itemRenderer}
                canMove={false}
                defaultTimeStart={moment().add(-17, "day")}
                defaultTimeEnd={moment().add(4, "day")}
            />
        </div>
    </div>
  )
    ;
};

export default GanttChart;
