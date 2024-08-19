import {selectColor} from "@/utils/utils";
import * as React from "react";
import Link from "next/link";

export const itemRenderer = ({item, itemContext, getItemProps}) => {
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
    </div>
  );
};

export const groupsRenderer = ({group}) => {
  return (
    <div className="custom-group">
      <Link href={`consultants/${group.id}`} className="consultant-details__link">
        <span className="title">{group.title}</span>
      </Link>
    </div>
  )
}