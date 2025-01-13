import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import {ReactNode} from "react";
import './accordion-component.css'

type Props = {
  title: string,
  content: ReactNode
}

export const AccordionComponent = ({title, content}: Props) => {
  return (
    <Accordion className="accordion__container">
      <AccordionSummary
        expandIcon={<ExpandMoreIcon/>}
        aria-controls={title}
        id={title}
      >
        {title}
      </AccordionSummary>
      <AccordionDetails>
        {content}
      </AccordionDetails>
    </Accordion>
  );
};
