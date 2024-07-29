"use client"
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, {SelectChangeEvent} from '@mui/material/Select';
import {Checkbox, ListItemText} from "@mui/material";
import {Dispatch, SetStateAction} from "react";

type Props = {
  allPts: string[];
  handlePtsSelection: (el: string[]) => void,
  responsiblePt: string[],
  setResponsiblePt: Dispatch<SetStateAction<string[]>>
}
const Multiselect = ({allPts, handlePtsSelection, setResponsiblePt, responsiblePt}: Props) => {
  const handleChange = (event: SelectChangeEvent<typeof responsiblePt>) => {
    const {
      target: {value},
    } = event;
    const selectionArray = typeof value === 'string' ? value.split(',') : value;
    setResponsiblePt(selectionArray);
    handlePtsSelection(selectionArray);
  };
  return (
    <div>
      <FormControl sx={{m: 1, width: 300}}>
        <InputLabel id="demo-multiple-checkbox-label">Choose pts</InputLabel>
        <Select
          labelId="demo-multiple-checkbox-label"
          id="demo-multiple-checkbox"
          multiple
          value={responsiblePt}
          onChange={handleChange}
          input={<OutlinedInput label="Choose pts"/>}
          renderValue={(selected) => selected.join(', ')}
        >
          {allPts.map((name) => (
            <MenuItem key={name} value={name}>
              <Checkbox checked={responsiblePt.indexOf(name) > -1}/>
              <ListItemText primary={name}/>
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </div>
  );
}
export default Multiselect;