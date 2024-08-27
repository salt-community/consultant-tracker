import "./single-detail-field.css"
type Props ={
  title: string,
  content: string
}
const SingleDetailField = ({title, content}:Props) => {
  return (
    <div className="details__wrapper">
      <p className="details__title">{title}:</p>
      <p>{content}</p>
    </div>
  );
};

export default SingleDetailField;