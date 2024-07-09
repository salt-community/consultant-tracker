"use client";

import Typography from "@mui/material/Typography";
import Breadcrumbs from "@mui/material/Breadcrumbs";
import Link from "next/link";
import { useDetailsContext } from "@/context/details";
import "./breadcrumbs.css";

const BreadcrumbsComponent = () => {
  const details = useDetailsContext();

  return (
    <Breadcrumbs aria-label="breadcrumb">
      <Link className="breadcrumb-link" href="/">
        Home
      </Link>
      <Typography color="text.primary">{details.name}</Typography>
    </Breadcrumbs>
  );
};

export default BreadcrumbsComponent;
