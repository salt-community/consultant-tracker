"use client";

import Link from "next/link";
import Indicator from "./table-legend/indicator/indicator";
import { useTableContext } from "@/context/context";

const Table = () => {
  const data = useTableContext();

  return (
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Client</th>
          <th>Details</th>
        </tr>
      </thead>
      <tbody>
        {data.data.map((item, index) => {
          const { id, name, client, status } = item;
          return (
            <tr key={index}>
              <td>
                <Indicator value={status} />
                {name}
              </td>
              <td>{client}</td>
              <td>
                <Link href={`/details/${id}`}>
                  <img src="details.svg" alt="details-icon" />
                </Link>
              </td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );
};

export default Table;
