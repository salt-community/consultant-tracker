import TablePagination from "@mui/material/TablePagination";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, RootState} from "../../store/store";
import {setPage, setRowsPerPage} from "../../store/slices/PaginationSlice";

const Pagination = () => {
  const dispatch = useDispatch<AppDispatch>();
  const totalItems = useSelector((state: RootState) => state.pagination.totalItems)
  const rowsPerPage = useSelector((state: RootState) => state.pagination.rowsPerPage)
  const page = useSelector((state: RootState) => state.pagination.page)
  const handleChangePage = (
    _event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number
  ) => {
    dispatch(setPage(newPage));
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    dispatch(setRowsPerPage(parseInt(event.target.value, 10)));
    dispatch(setPage(0));
  };
  return (
      <TablePagination
        rowsPerPageOptions={[5, 10]}
        component="div"
        count={totalItems}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
  );
};

export default Pagination;