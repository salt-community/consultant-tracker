import Indicator from "./indicator/indicator";
import "./table-legend.css";
import { status } from "@/mockData";

const TableLegend = () => {
  return (
    <>
      {status.map((status) => {
        const { id, value } = status;
        return (
          <div key={id} className="status-indicator__wrapper">
            <Indicator value={value} />
            <p>{value}</p>
          </div>
        );
      })}
    </>
  );
};

export default TableLegend;
