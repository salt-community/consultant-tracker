"use client";
import Timeline from "react-calendar-timeline";
import "react-calendar-timeline/lib/Timeline.css";
import moment from "moment";
import {
  ConsultantItemsType,
  ConsultantsCalendarType,
  RedDaysResponseType,
} from "@/types";
import "./gantt-chart.css";
import { verticalLineClassNamesForTime } from "@/utils/utils";
import { Dispatch, SetStateAction, useEffect, useState } from "react";
import TablePagination from "@mui/material/TablePagination";
import * as React from "react";
import { getRedDays } from "@/api";
import Loading from "@/components/loading/loading";
import Error from "@/components/error/error";
import {
  groupsRenderer,
  itemRenderer,
} from "@/components/gantt-chart/gantt-chart-renderers";
import BasicInfo from "../consultant-detail/basic-info/basic-info";

type Props = {
  itemsProps: ConsultantItemsType[];
  groupsProps: ConsultantsCalendarType[];
  totalItems: number;
  page: number;
  setPage: Dispatch<SetStateAction<number>>;
  setRowsPerPage: Dispatch<SetStateAction<number>>;
  rowsPerPage: number;
  loading: boolean;
  error: string;
  setOpen: Dispatch<SetStateAction<boolean>>;
  open: boolean;
};

const GanttChart = ({
  itemsProps,
  groupsProps,
  totalItems,
  page,
  setPage,
  error,
  rowsPerPage,
  setRowsPerPage,
  loading,
  setOpen,
  open,
}: Props) => {
  const [modalData, setModalData] = useState<ConsultantItemsType>();
  const [redDaysSE, setRedDaysSE] = useState<moment.Moment[]>([]);
  const [redDaysNO, setRedDaysNO] = useState<moment.Moment[]>([]);
  const [id, setId] = useState<string>();
  const [openTootlip, setOpenTooltip] = useState(false);

  const handleItemSelect = (itemId: string) => {
    const consultantItems = itemsProps.filter((el) => itemId == el.id)[0];
    setModalData(consultantItems);
    setId(consultantItems.group);
    setOpen(true);
    setOpenTooltip(true);
  };

  useEffect(() => {
    getRedDays().then((res: RedDaysResponseType) => {
      setRedDaysSE(res.redDaysSE.map((el) => moment(el)));
      setRedDaysNO(res.redDaysNO.map((el) => moment(el)));
    });
  }, []);

  const handleChangePage = (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number
  ) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };
  return loading ? (
    <Loading />
  ) : error.length !== 0 ? (
    <Error message={error} />
  ) : (
    groupsProps.length > 0 &&
    itemsProps.length > 0 ? (
      <div>
        <TablePagination
          rowsPerPageOptions={[5, 10]}
          component="div"
          count={totalItems}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
          <div className="gantt-chart__wrapper">
            <Timeline
              groups={groupsProps}
              items={itemsProps}
              onItemSelect={handleItemSelect}
              itemRenderer={itemRenderer}
              groupRenderer={groupsRenderer}
              canMove={false}
              onItemClick={handleItemSelect}
              defaultTimeStart={moment().add(-17, "day")}
              defaultTimeEnd={moment().add(4, "day")}
              sidebarWidth={250}
              lineHeight={35}
              verticalLineClassNamesForTime={(timeStart, timeEnd) =>
                verticalLineClassNamesForTime(
                  timeStart,
                  timeEnd,
                  redDaysSE,
                  redDaysNO
                )
              }
            />
            {id && id.length > 0 && modalData && (
              <div className={open ? "show" : "hide"}>
                <BasicInfo
                  id={id}
                  modalData={modalData}
                  openTooltip={openTootlip}
                  setOpenTooltip={setOpenTooltip}
                />
              </div>
            )}
          </div>
      </div>
    ) : (<div className="gantt-chart__no-data">No data matching filter criteria</div>)
  )

};
export default GanttChart;
