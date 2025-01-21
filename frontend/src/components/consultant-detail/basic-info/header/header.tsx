import "./header.css";
import HeaderAvatar from "./avatar/avatar.tsx";

type Props = {
  name: string;
  githubImageUrl: string;
};

const BasicInfoHeader = ({ name, githubImageUrl }: Props) => {
  return (
    <section className="basic-info-header__container">
      <HeaderAvatar githubImageUrl={githubImageUrl} />
      <h2>{name}</h2>
    </section>
  );
};
export default BasicInfoHeader;
