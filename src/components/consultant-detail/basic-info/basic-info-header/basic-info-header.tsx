import HeaderAvatar from "@/components/consultant-detail/basic-info/basic-info-header/header-avatar/header-avatar";
import './basic-info-header.css'
import HeaderName from "@/components/consultant-detail/basic-info/basic-info-header/header-name/header-name";
import HeaderStatus from "@/components/consultant-detail/basic-info/basic-info-header/header-status/header-status";

type Props = {
  name: string,
  status: string,
}
const BasicInfoHeader = ({name, status}: Props) => {
  return (
    <section className="basic-info-header__container">
      <HeaderAvatar />
      <div className="basic-info-header__wrapper">
        <HeaderName name={name}/>
        <HeaderStatus status={status} />
      </div>
    </section>
  );
};

export default BasicInfoHeader;