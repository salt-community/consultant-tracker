import "./single-detail-field.css";
import { MdOutlineContentCopy } from "react-icons/md";
type Props = {
  title?: string;
  content: string;
  onClick?: ()=>void
};
const SingleDetailField = ({ title, content, onClick }: Props) => {
  return (
    <div className="details__wrapper" onClick={onClick && onClick}>
      {title && <p className="details__title">{title}:</p>}
      <p className={onClick ? "content-copy" : ""}>{content} {onClick && <MdOutlineContentCopy />}</p>
    </div>
  );
};

export default SingleDetailField;
