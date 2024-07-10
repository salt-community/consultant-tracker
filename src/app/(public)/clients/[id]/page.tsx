import BreadcrumbsComponent from "@/components/breadcrumbs/breadcrumbs";
import ClientsDetails from "@/components/client-detail/clients-details";

const Page = () => {
  return (
    <>
      <BreadcrumbsComponent variant="client"/>
      <div className="detail-client__wrapper">
        <ClientsDetails />
      </div>
    </>
  );
};

export default Page;