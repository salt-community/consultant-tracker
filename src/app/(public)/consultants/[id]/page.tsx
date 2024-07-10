import "./page.css";
import BasicInfo from "@/components/consultant-detail/basic-info/basic-info";
import BreadcrumbsComponent from "@/components/breadcrumbs/breadcrumbs";
import CardDetails from "@/components/card-details/card-details";

const ConsultantDetail = () => {
  return (
    <>
      <BreadcrumbsComponent variant="consultant" />
      <div className="detail-consultant__wrapper">
        <BasicInfo />
        <CardDetails />
      </div>
    </>
  );
};

export default ConsultantDetail;
