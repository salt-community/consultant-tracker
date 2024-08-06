"use client";
import Timeline from "react-calendar-timeline";
import "react-calendar-timeline/lib/Timeline.css";
import moment from "moment";
import { ConsultantItemsType, ConsultantsCalendarType } from "@/types";
import "./gantt-chart.css";
import { selectColor } from "@/utils/utils";
import { useEffect, useState } from "react";
import {
  Popover,
  PopoverClose,
  PopoverContent,
  PopoverDescription,
  PopoverHeading,
  PopoverTrigger,
} from "../popover/popover";

type Props = {
  itemsProps: ConsultantItemsType[];
  groupsProps: ConsultantsCalendarType[];
};
const GanttChart = ({ itemsProps, groupsProps }: Props) => {
  const [modalData, setModalData] = useState<ConsultantItemsType>();

  const handleItemSelect = (itemId, e, time) => {
    const consultantItems = itemsProps.filter((el) => itemId == el.id)[0];
    setModalData(consultantItems);
  };
  useEffect(() => {}, [modalData]);
  const itemRenderer = ({ item, itemContext, getItemProps }) => {
    const chosenColor = selectColor(item.title);
    const background = itemContext.selected
      ? chosenColor
      : item.itemProps.style.background;
    const borderColor = itemContext.selected
      ? "black"
      : item.itemProps.style.borderColor;

    return (
      <div
        {...getItemProps({
          style: {
            background,
            color: item.color,
            borderColor,
            borderStyle: "solid",
            borderWidth: 1,
            borderRadius: 0,
            borderLeftWidth: itemContext.selected ? 3 : 1,
            borderRightWidth: itemContext.selected ? 3 : 1,
          },
        })}
      >
        <Popover>
          <PopoverTrigger/>
          <PopoverContent className="Popover">
            <PopoverHeading>{item.details.name}</PopoverHeading>
            <PopoverDescription>
              <div className="popover-descr__basic-details">
                <p>Client: AstraZeneca</p>
                <p>Total Days Worked: {item.details.totalWorkedDays}</p>
                <p>Remaining Days: {item.details.totalRemainingDays}</p>
                <p>
                  Total Vacay Days Used: {item.details.totalVacationDaysUsed}
                </p>
              </div>
              <div className="popover-descr__item">
                <h3>{item.title} - {item.end_time.diff(item.start_time, 'day') + 1} days</h3>
                <p>Start: {item.start_time.format('ddd, DD-MMM-YY')}</p>
                <p>End: {item.end_time.format('ddd, DD-MMM-YY')}</p>
              </div>
            </PopoverDescription>
            <PopoverClose>Close</PopoverClose>
          </PopoverContent>
        </Popover>
      </div>
    );
  };
  return (
    groupsProps.length > 0 &&
    itemsProps.length > 0 && (
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
  );
};

export default GanttChart;
