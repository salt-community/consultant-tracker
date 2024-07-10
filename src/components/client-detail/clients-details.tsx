"use client"
import Header from "@/components/client-detail/header/header";
import {useClientsContext} from "@/context/clients";
import {usePathname} from "next/navigation";
import ContactPeople from "./contact-people/contact-people";

const ClientsDetails = () => {
  const client = useClientsContext();
  const idClient = usePathname().split("/").pop();
  const clientData = client.data.filter(el => el.id == idClient)[0];
  const {name, contactPeople} = clientData;
  return (
    <div>
      <Header name={name}/>
      <ContactPeople contactPeople={contactPeople}/>
    </div>
  );
};

export default ClientsDetails;