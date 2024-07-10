import {useClientsContext} from "@/context/clients";
import SingleConsultant from "@/components/client-detail/consultants-list/single-consultant/single-consultant";
import {usePathname} from "next/navigation";

const ConsultantsList = () => {
  const client = useClientsContext();
  const idParam = usePathname().split("/").pop();
  const singleClient = client.data.filter(el => el.id === idParam)[0]
  return (
    <div>
      {singleClient.listOfConsultants.map(consultant => {
        const {name, id} = consultant
        return (
          <SingleConsultant name={name} id={id} key={id}/>
        )
      })}
    </div>
  );
};

export default ConsultantsList;