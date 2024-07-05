import React from 'react';
import Typography from '@mui/material/Typography';
import Breadcrumbs from '@mui/material/Breadcrumbs';
import Link from '@mui/material/Link';

type Props = {
  current: string
}

const BreadcrumbsComponent = ({ current}: Props) => {


  return (
    <Breadcrumbs aria-label="breadcrumb">
      <Link underline="hover" color="inherit" href="/">
        Home
      </Link>
      <Typography color="text.primary">{current}</Typography>
    </Breadcrumbs>
  );
};

export default BreadcrumbsComponent;