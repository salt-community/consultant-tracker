import { Button } from "@mui/material";
import { PiPencilSimpleLineThin } from "react-icons/pi";

type Props = {
  readonly: boolean;
  handleClick: (v: boolean) => void;
};

const Edit = ({ readonly, handleClick }: Props) => {
  return (
    <>
      {readonly ? (
        <PiPencilSimpleLineThin
          onClick={() => handleClick(false)}
          className="header-name__edit"
        />
      ) : (
        <div>
          <Button type="submit" variant="text">
            Submit
          </Button>
          <Button
            type="button"
            variant="text"
            color="error"
            onClick={() => handleClick(true)}
          >
            Cancel
          </Button>
        </div>
      )}
    </>
  );
};

export default Edit;
