"use client";
import "react-calendar-timeline/lib/Timeline.css";
import "./gantt-chart.css";
import {encodeString, mapConsultantsToCalendarItems, mapGroups} from "@/utils/utils";
import {useEffect} from "react";
import * as React from "react";
import {getConsultantsData} from "@/api";
import Loading from "@/components/loading/loading";
import Error from "@/components/error/error";
import BasicInfo from "../consultant-detail/basic-info/basic-info";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, RootState} from "@/store/store";
import {
  setError,
  setGroups,
  setItems,
  setLoading,
  setOpenModal,
} from "@/store/slices/GanttChartSlice";
import Pagination from "@/components/pagination/pagination";
import {setTotalItems} from "@/store/slices/PaginationSlice";
import TimelineComponent from "@/components/timeline-component/timelineComponent";
import Legend from "@/components/gantt-chart/legend/legend";
import AccordionComponent from "@/components/accordion/accordion-component";

const GanttChart = () => {
  const dispatch = useDispatch<AppDispatch>();

  const page = useSelector((state: RootState) => state.pagination.page)
  const rowsPerPage = useSelector((state: RootState) => state.pagination.rowsPerPage)

  const id = useSelector((state: RootState) => state.ganttChart.id)
  const error = useSelector((state: RootState) => state.ganttChart.error)
  const loading = useSelector((state: RootState) => state.ganttChart.loading)

  const filterPts = useSelector((state: RootState) => state.filterField.filterPts)
  const filterClients = useSelector((state: RootState) => state.filterField.filterClients)
  const debounceFilterName = useSelector((state: RootState) => state.filterField.debounceFilterName)
  const includePgps = useSelector((state: RootState) => state.filterField.includePgps)


  const fetchConsultantsData = () => {
    const filterPtsEncodeUriString = encodeString(filterPts, "pt");
    const filterClientsEncodeUriString = encodeString(filterClients, "client");
    dispatch(setLoading(true));
    getConsultantsData(page, rowsPerPage, filterClientsEncodeUriString, filterPtsEncodeUriString, debounceFilterName, includePgps)
      .then((res) => {
        dispatch(setItems(mapConsultantsToCalendarItems(res)));
        dispatch(setTotalItems(res.totalConsultants));
        dispatch(setGroups(mapGroups(res)));
      })
      .catch(() => {
        dispatch(setError("Failed to fetch resources"));
      })
      .finally(() => {
        dispatch(setOpenModal(false));
        dispatch(setLoading(false))
      })
  };

  useEffect(() => {
    fetchConsultantsData();
  }, [filterPts, filterClients, debounceFilterName, rowsPerPage, page, includePgps]);

  return loading ? <Loading/> : error.length !== 0
    ? <Error message={error}/> :
    <div>
      <AccordionComponent title="Legend" content={<Legend/>}/>
      <Pagination/>
      <div className="gantt-chart__wrapper">
        <TimelineComponent/>
        {id.length > 0 && (
          <BasicInfo />
        )}
      </div>
    </div>
};
export default GanttChart;
