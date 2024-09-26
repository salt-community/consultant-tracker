import "react-calendar-timeline/lib/Timeline.css";
import "./gantt-chart.css";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../store/store";
import {
  setError,
  setGroups,
  setItems,
  setLoading,
  setOpenModal,
} from "../../store/slices/GanttChartSlice";
import {Pagination, TimelineComponent, AccordionComponent, Loading, Error, BasicInfo} from "../../components";
import { setTotalItems } from "../../store/slices/PaginationSlice";
import Legend from "./legend/legend";
import { getConsultantsData } from "../../api";
import {
  mapConsultantsToCalendarItems,
  mapGroups,
} from "../../utils/utils.ts";
import { useAuth } from "@clerk/clerk-react";
import { template } from "../../constants";

export const GanttChart = () => {
  const dispatch = useDispatch<AppDispatch>();

  const page = useSelector((state: RootState) => state.pagination.page);
  const rowsPerPage = useSelector(
    (state: RootState) => state.pagination.rowsPerPage
  );

  const id = useSelector((state: RootState) => state.ganttChart.id);
  const error = useSelector((state: RootState) => state.ganttChart.error);
  const loading = useSelector((state: RootState) => state.ganttChart.loading);

  const filterPts = useSelector(
    (state: RootState) => state.filterField.filterPts
  );
  const filterClients = useSelector(
    (state: RootState) => state.filterField.filterClients
  );
  const debounceFilterName = useSelector(
    (state: RootState) => state.filterField.debounceFilterName
  );
  const includePgps = useSelector(
    (state: RootState) => state.filterField.includePgps
  );

  const { getToken, signOut } = useAuth();

  const fetchConsultantsData = async () => {
    const params = {
      page: page.toString(),
      pageSize: rowsPerPage.toString(),
      pt: filterPts.toString(),
      client: filterClients.toString(),
      name: debounceFilterName.toString(),
      includePgps: includePgps.toString(),
    };
    const searchParams = new URLSearchParams(params);
    const token = await getToken({template});

    dispatch(setLoading(true));
    if (!token) {
      await signOut();
      return;
    }
    getConsultantsData(
     searchParams,
      token
    )
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
        dispatch(setLoading(false));
      });
  };

  useEffect(() => {
    void fetchConsultantsData();
  }, [
    filterPts,
    filterClients,
    debounceFilterName,
    rowsPerPage,
    page,
    includePgps,
  ]);

  return loading ? (
    <Loading />
  ) : error.length !== 0 ? (
    <Error message={error} />
  ) : (
    <div>
      <AccordionComponent title="Legend" content={<Legend />} />
      <Pagination />
      <div className="gantt-chart__wrapper">
        <TimelineComponent />
        {id.length > 0 && <BasicInfo />}
      </div>
    </div>
  );
};
