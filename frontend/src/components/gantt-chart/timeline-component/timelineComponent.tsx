import {
  groupsRenderer,
  itemRenderer,
} from "../gantt-chart-renderers";
import moment from "moment";
import {verticalLineClassNamesForTime} from "../../../utils/utils";
import Timeline from "react-calendar-timeline";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, RootState} from "../../../store/store";
import {
  setId,
  setModalData,
  setOpenModal,
  setOpenTimeItemDetails,
  setSelectedId,
} from "../../../store/slices/GanttChartSlice";

export const TimelineComponent = () => {
  const dispatch = useDispatch<AppDispatch>();
  const items = useSelector((state: RootState) => state.ganttChart.items);
  const groups = useSelector((state: RootState) => state.ganttChart.groups);
  const redDaysSE = useSelector(
    (state: RootState) => state.ganttChart.redDaysSE
  );
  const redDaysNO = useSelector(
    (state: RootState) => state.ganttChart.redDaysNO
  );
  const handleItemSelect = (itemId: string) => {
    const consultantItems = items.filter((el) => itemId == el.id)[0];
    dispatch(setModalData(consultantItems));
    dispatch(setId(consultantItems.group));
    dispatch(setOpenModal(true));
    dispatch(setOpenTimeItemDetails(true));
    setTimeout(() => window.scrollTo({top: 3000, behavior: "smooth"}), 200);
    dispatch(setSelectedId(consultantItems.group));
  };

  return items.length > 0 ? (
    <Timeline
      groups={groups}
      items={items}
      onItemSelect={handleItemSelect}
      itemRenderer={itemRenderer}
      groupRenderer={groupsRenderer}
      canMove={false}
      onItemClick={handleItemSelect}
      defaultTimeStart={moment().add(-6, "month")}
      defaultTimeEnd={moment().add(5, "month")}
      sidebarWidth={250}
      lineHeight={35}
      verticalLineClassNamesForTime={(timeStart, timeEnd) =>
        verticalLineClassNamesForTime(timeStart, timeEnd, redDaysSE, redDaysNO)
      }
    />
  ) : (
    <div className="gantt-chart__no-data">
      No data matching filter criteria{" "}
    </div>
  );
};

