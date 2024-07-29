import Link from "next/link";

type Props ={
  id: string,
  name: string
}
const SingleConsultant = ({id, name}: Props) => {
  return (
    <div>
      <h1>{name}</h1>
      <Link href={`/consultants/${id}`}>Details</Link>
    </div>
  );
};

export default SingleConsultant;