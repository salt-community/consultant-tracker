import {statusOptions} from "@/mockData";
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import {InputLabel} from "@mui/material";
import Select, {SelectChangeEvent} from '@mui/material/Select';
import './select-status.css'

type Props = {
  status: string,
  handleChange: (e: SelectChangeEvent) => void
}
const SelectStatus = ({status, handleChange}: Props) => {

  return (
    <FormControl className="select-status__container">
      <InputLabel id="status">Choose Status</InputLabel>
      <Select
        id="status"
        value={status}
        label="Choose status"
        onChange={handleChange}
      >
        {statusOptions.map((status, index) => {
          return (<MenuItem value={status.toLowerCase()} key={index}>{status}</MenuItem>)
        })}
      </Select>
    </FormControl>
  );
};

export default SelectStatus;