import React from 'react';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import {SelectChangeEvent} from "@mui/material/Select";
import './filter-by.css'

type Props = {
  value: string,
  handleChange: (e: SelectChangeEvent) => void
}

const FilterBy = ({value, handleChange}: Props) => {
  return (
    <FormControl className="filter-by__container">
      <RadioGroup
        aria-labelledby="radio-buttons-group-label"
        defaultValue="female"
        name="radio-buttons-group"
        value={value}
        onChange={handleChange}
        className="filter-by__radio"
      >
        <FormControlLabel value="Consultant" control={<Radio/>} label="Consultant"/>
        <FormControlLabel value="Client" control={<Radio/>} label="Client"/>
      </RadioGroup>
    </FormControl>
  );
};

export default FilterBy;