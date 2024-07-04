
import Client from "@/components/consultant-detail/client/client";
import PersonalData from "@/components/consultant-detail/personal-data/personal-data";
import Schedule from "@/components/consultant-detail/schedule/schedule";
import PageWrapper from "@/components/page-wrapper/page-wrapper";

const ConsultantDetail = () => {
  return (
    <PageWrapper>
      <PersonalData />
      <Schedule />
      <Client />
    </PageWrapper>
  );
};

export default ConsultantDetail;
