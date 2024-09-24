import {useEffect} from 'react';
import {groupsRenderer, itemRenderer} from "../gantt-chart/gantt-chart-renderers.tsx";
import moment from "moment";
import {verticalLineClassNamesForTime} from "../../utils/utils";
import Timeline from "react-calendar-timeline";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, RootState} from "../../store/store";
import {
  setId,
  setModalData,
  setOpenModal,
  setOpenTimeItemDetails,
  setRedDaysNO,
  setRedDaysSE,
  setSelectedId
} from "../../store/slices/GanttChartSlice";
import {getRedDays} from "../../api";
import {RedDaysResponseType} from "../../types";

const TimelineComponent = () => {
  const dispatch = useDispatch<AppDispatch>();
  const items = useSelector((state: RootState) => state.ganttChart.items)
  const groups = useSelector((state: RootState) => state.ganttChart.groups)
  const redDaysSE = useSelector((state: RootState) => state.ganttChart.redDaysSE)
  const redDaysNO = useSelector((state: RootState) => state.ganttChart.redDaysNO)
  const token = useSelector((state: RootState) => state.token.token)
  const handleItemSelect = (itemId: string) => {
    const consultantItems = items.filter((el) => itemId == el.id)[0];
    dispatch(setModalData(consultantItems));
    dispatch(setId(consultantItems.group));
    dispatch(setOpenModal(true));
    dispatch(setOpenTimeItemDetails(true));
    setTimeout(()=>window.scrollTo({top: 3000, behavior: "smooth"}),200)
    dispatch(setSelectedId(consultantItems.group))
  };
  useEffect(() => {
    token != "" && getRedDays(token)
      .then((res: RedDaysResponseType) => {
        dispatch(setRedDaysSE(res.redDaysSE.map((el) => moment(el))));
        dispatch(setRedDaysNO(res.redDaysNO.map((el) => moment(el))));
      });
  }, [token]);

  return (
    items.length > 0
      ? <Timeline
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
          verticalLineClassNamesForTime(
            timeStart,
            timeEnd,
            redDaysSE,
            redDaysNO
          )
        }
      />
      :
      <div className="gantt-chart__no-data"> No data matching filter criteria </div>
  );
};

export default TimelineComponent;