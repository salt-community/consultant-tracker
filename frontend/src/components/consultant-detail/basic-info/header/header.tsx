import "./header.css";
import HeaderAvatar from "./avatar/avatar.tsx";

type Props = {
  name: string;
  gitHubImgUrl: string;
};

const BasicInfoHeader = ({ name, gitHubImgUrl }: Props) => {
  return (
    <section className="basic-info-header__container">
      <HeaderAvatar gitHubImgUrl={gitHubImgUrl} />
      <h2>{name}</h2>
    </section>
  );
};
export default BasicInfoHeader;
