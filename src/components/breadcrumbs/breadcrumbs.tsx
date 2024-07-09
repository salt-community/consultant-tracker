"use client";

import React, { useEffect } from "react";
import Typography from "@mui/material/Typography";
import Breadcrumbs from "@mui/material/Breadcrumbs";
import Link from "next/link";
import { useDetailsContext } from "@/context/details";

const BreadcrumbsComponent = () => {
  const details = useDetailsContext();
  let name = details.name;
  useEffect(() => {
    name = details.name;
  }, [details.name]);

  return (
    <Breadcrumbs aria-label="breadcrumb">
      <Link color="inherit" href="/">
        Home
      </Link>
      <Typography color="text.primary">{name}</Typography>
    </Breadcrumbs>
  );
};

export default BreadcrumbsComponent;
