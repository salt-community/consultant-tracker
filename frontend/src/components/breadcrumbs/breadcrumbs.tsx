"use client";

import Typography from "@mui/material/Typography";
import Breadcrumbs from "@mui/material/Breadcrumbs";
import Link from "next/link";
import { useDetailsContext } from "@/context/details";
import "./breadcrumbs.css";
import {useClientsContext} from "@/context/clients";
type Props={
  variant: string
}
const BreadcrumbsComponent = ({variant}:Props) => {
  const details = useDetailsContext();
  const clients =  useClientsContext();
  const content = ()=>{
    switch(variant){
      case "client": return clients.fullName;
      default: return details.fullName
    }
  }
  return (
    <Breadcrumbs aria-label="breadcrumb">
      <Link className="breadcrumb-link" href="/">
        Home
      </Link>
      <Typography color="text.primary">{content()}</Typography>
    </Breadcrumbs>
  );
};

export default BreadcrumbsComponent;
