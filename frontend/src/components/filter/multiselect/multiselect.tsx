"use client"
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, {SelectChangeEvent} from '@mui/material/Select';
import {Checkbox, ListItemText} from "@mui/material";

type Props = {
  fullList: string[];
  handleSelection: (el: string[]) => void,
  selected: string[],
  setSelection: (arr: string[]) => void,
  label: string
}
const Multiselect = ({fullList, handleSelection, selected, setSelection, label}: Props) => {
  const handleChange = (event: SelectChangeEvent<string[]>) => {
    const {
      target: {value},
    } = event;
    const selectionArray = typeof value === 'string' ? value.split(',') : value;
    setSelection(selectionArray);
    handleSelection(selectionArray);
  };
  return (
    <div>
      <FormControl sx={{m: 1, width: 300}}>
        <InputLabel id="demo-multiple-checkbox-label">{label}</InputLabel>
        <Select
          labelId="demo-multiple-checkbox-label"
          id="demo-multiple-checkbox"
          multiple
          value={selected}
          onChange={handleChange}
          input={<OutlinedInput label={label}/>}
          renderValue={(selected) => selected.join(', ')}
        >
          {fullList.length > 0 && fullList.map((name) => (
            <MenuItem key={name} value={name}>
              <Checkbox checked={selected.indexOf(name) > -1}/>
              <ListItemText primary={name}/>
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </div>
  );
}
export default Multiselect;