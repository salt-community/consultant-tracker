"use client"
import Header from "@/components/client-detail/header/header";
import {useClientsContext} from "@/context/clients";
import {usePathname} from "next/navigation";

const ClientsDetails = () => {
  const client= useClientsContext();
  const idClient = usePathname().split("/").pop();
  const name = client.data.filter(el=>el.id === idClient)[0].name;
  return (
    <div>
      <Header name={name} />
    </div>
  );
};

export default ClientsDetails;