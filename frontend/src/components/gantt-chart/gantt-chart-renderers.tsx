import {
  setId,
  setModalData,
  setOpenModal,
  setOpenTimeItemDetails,
  setSelectedId
} from "../../store/slices/GanttChartSlice";
import './gantt-chart-renderers.css'

import { selectColor } from "../../utils/utils";
import { AppDispatch, RootState } from "../../store/store";
import { useDispatch, useSelector } from "react-redux";
type Props={
  item: any,
  itemContext: any,
  getItemProps: any
}
export const itemRenderer = ({item, itemContext, getItemProps}:Props) => {
  const chosenColor = selectColor(item.title);
  const background = itemContext.selected
    ? chosenColor
    : item.itemProps!.style!.background;
  const borderColor = itemContext.selected
    ? "black"
    : item.itemProps!.style!.borderColor;
  return (
    <div
      {...getItemProps({
        style: {
          background,
          borderColor,
          borderStyle: "solid",
          borderWidth: 1,
          opacity: 0.8,
          borderRadius: 0,
          borderLeftWidth: itemContext.selected ? 3 : 1,
          borderRightWidth: itemContext.selected ? 3 : 1,
        },
      })}
    ></div>
  );
};

type GroupProps = {
  group: any
}

export const groupsRenderer = ({group}:GroupProps) => {
  const dispatch = useDispatch<AppDispatch>();
  const items = useSelector((state: RootState) => state.ganttChart.items)
  const selectedId = useSelector((state: RootState) => state.ganttChart.selectedId)

  const handleItemSelect = (groupId: string) => {
    const consultantItems = items.filter((el) => groupId == el.group)[0];
    dispatch(setModalData(consultantItems));
    dispatch(setId(consultantItems.group));
    dispatch(setOpenModal(true));
    dispatch(setOpenTimeItemDetails(false));
    setTimeout(()=>window.scrollTo({top: 3000, behavior: "smooth"}),200)
    dispatch(setSelectedId(groupId));
  };
  return (
    <span className={selectedId === group.id ? "title active": "title"} onClick={() => handleItemSelect(group.id)}>{group.title}</span>
  );
};
