import "./page.css";
import BasicInfo from "@/components/consultant-detail/basic-info/basic-info";
import BreadcrumbsComponent from "@/components/breadcrumbs/breadcrumbs";
import CardDetails from "@/components/card-details/card-details";

const ConsultantDetail = () => {
  return (
    <>
      <BreadcrumbsComponent />
      <div className="detail-page__wrapper">
        <section className="section-consultants">
          <div className="detail-page__right-side__wrapper">
            <BasicInfo />
            <CardDetails/>
          </div>
        </section>
      </div>
    </>
  );
};

export default ConsultantDetail;
