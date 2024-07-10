"use client"
import Header from "@/components/client-detail/header/header";
import {useClientsContext} from "@/context/clients";
import {usePathname} from "next/navigation";
import ContactPeople from "./contact-people/contact-people";

const ClientsDetails = () => {
  const client= useClientsContext();
  const idClient = usePathname().split("/").pop();
  const name = client.data.filter(el=>el.id == idClient)[0].name;
  const clientData = client.data.filter(el=>el.id == idClient)[0];

  return (
    <div>
      <Header name={clientData.name} />
      <ContactPeople contactPeople={clientData.contactPeople}/>
    </div>
  );
};

export default ClientsDetails;