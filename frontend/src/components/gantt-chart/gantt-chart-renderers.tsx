import {selectColor} from "@/utils/utils";
import * as React from "react";
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
  return (
    <span className="title">{group.title}</span>
  );
};
