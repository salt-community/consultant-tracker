import React from "react";
import Edit from "./edit/edit";
import "./edit-card.css";

type Props = {
  title: string;
  readonly: boolean;
  handleClick: (v: boolean) => void;
};

const EditCard = ({ title, readonly, handleClick }: Props) => {
  return (
    <div className="edit-card__container">
      <h3>{title}</h3>
      <Edit readonly={readonly} handleClick={handleClick} />
    </div>
  );
};

export default EditCard;
