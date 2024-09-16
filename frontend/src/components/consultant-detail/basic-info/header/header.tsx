import HeaderAvatar from "./avatar/avatar";
import "./header.css";

type Props = {
  name: string;
};

const BasicInfoHeader = ({ name }: Props) => {
  return (
    <section className="basic-info-header__container">
      <HeaderAvatar />
      <h2>{name}</h2>
    </section>
  );
};

export default BasicInfoHeader;
