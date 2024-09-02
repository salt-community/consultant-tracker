import "./single-detail-field.css";
type Props = {
  title: string;
  content: string;
};
const SingleDetailField = ({ title, content }: Props) => {
  let content2 = "";
  if (content.includes("@")) {
    const splitContent = content.split("@");
    content = splitContent[0];
    content2 = splitContent[1];
  }
  return (
    <div className="details__wrapper">
      <p className="details__title">{title}:</p>
      <p>{content}</p>
      {content2.length > 0 && <p>{`@${content2}`}</p>}
    </div>
  );
};

export default SingleDetailField;
