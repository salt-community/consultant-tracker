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
import TablePagination from "@mui/material/TablePagination";
import * as React from "react";

type Props = {
  itemsProps: ConsultantItemsType[];
  groupsProps: ConsultantsCalendarType[];
  totalItems: number
};
const GanttChart = ({ itemsProps, groupsProps, totalItems }: Props) => {
  const [modalData, setModalData] = useState<ConsultantItemsType>();
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);

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
                <p>Client: {item.details.client}</p>
                <p>Total Days Worked: {item.details.totalWorkedDays}</p>
                <p>Remaining Days: {item.details.totalRemainingDays}</p>
                <p>Total worked hours: {item.details.totalWorkedHours}</p>
                <p>Total remaining hours: {item.details.totalRemainingHours}</p>
                <p>
                  Total Vacay Days Used: {item.details.totalVacationDaysUsed}
                </p>
              </div>
              <div className="popover-descr__item">
                <h3>{item.title} - {item.details.totalDays} days</h3>
                <p>{item.details.projectName}</p>
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
  //TODO update holidays / fetch from backend
  const holidays = [
    moment("01.01.2024"),
    moment("06.01.2024"),
  ];
  const verticalLineClassNamesForTime = (timeStart, timeEnd) => {
    const currentTimeStart = moment(timeStart);
    const currentTimeEnd = moment(timeEnd);

    let classes = [];
    for (let holiday of holidays) {
      if (
        holiday.isSame(currentTimeStart, "day") &&
        holiday.isSame(currentTimeEnd, "day")
      ) {
        classes.push("holiday");
      }
    }

    return classes;
  };
  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
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
            verticalLineClassNamesForTime={verticalLineClassNamesForTime}
          />
        </div>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={totalItems}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </div>
    )
  );
};

export default GanttChart;
