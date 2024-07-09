import {SyntheticEvent} from "react";
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import {tabs} from "@/mockData";
import "./tabs.css"

type Props = {
  value: string,
  handleChange: (event: SyntheticEvent, newValue: string) => void;
}
const TabsComponent = ({handleChange, value}: Props) => {

  return (
    <Box sx={{width: '100%'}}>
      <Tabs
        value={value}
        onChange={handleChange}
        textColor="secondary"
        indicatorColor="secondary"
        aria-label="secondary tabs example"
      >
        {tabs.map(tab => {
          const {label, id, value} = tab
          return <Tab value={value} label={label} key={id}/>
        })}


      </Tabs>
    </Box>
  );
};

export default TabsComponent;