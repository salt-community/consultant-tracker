import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { ReactNode, useState } from "react";
import './accordion-component.css';

type Props = {
  titleIcon?: ReactNode
  title?: string;
  content: ReactNode;

};


export const AccordionComponent = ({titleIcon, title, content }: Props) => {
  const [isExpanded, setIsExpanded] = useState(false);

  const handleChange = (_event: React.SyntheticEvent, expanded: boolean) => {
    setIsExpanded(expanded);
  };

  return (
    <Accordion 
      className={`accordion__container ${isExpanded ? 'accordion__expanded' : ''}`} 
      onChange={handleChange}
    >
      <AccordionSummary
        expandIcon={<ExpandMoreIcon />}
        aria-controls={title}
        id={title}
      >
        {titleIcon}
      </AccordionSummary>
      <AccordionDetails>
        {content}
      </AccordionDetails>
    </Accordion>
  );
};
