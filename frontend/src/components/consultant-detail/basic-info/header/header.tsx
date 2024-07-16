import HeaderAvatar from "@/components/consultant-detail/basic-info/header/avatar/avatar";
import "./header.css";
import HeaderName from "@/components/consultant-detail/basic-info/header/name/name";
import HeaderStatus from "@/components/consultant-detail/basic-info/header/status/status";

type Props = {
  name: string;
  status: string;
};
const BasicInfoHeader = ({ name, status }: Props) => {
  return (
    <section className="basic-info-header__container">
      <HeaderAvatar />
      <div className="basic-info-header__wrapper">
        <HeaderName name={name} />
        <HeaderStatus status={status} />
      </div>
    </section>
  );
};

export default BasicInfoHeader;
