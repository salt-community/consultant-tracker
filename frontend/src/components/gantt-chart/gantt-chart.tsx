"use client"
import {useClientsContext} from "@/context/clients";
import SingleConsultant from "@/components/gantt-chart/single-consultant/single-consultant";
import {usePathname} from "next/navigation";
import Timeline from "react-calendar-timeline";
import "react-calendar-timeline/lib/Timeline.css";
import moment from "moment";
import {consultantsCalendar, consultantItems} from "@/mockData";
import {useEffect, useState} from "react";
import {ConsultantItemsType, ConsultantsCalendarType} from "@/types";
import {getDatesForRemainingTime} from "@/helperMethods";
import "./gantt-chart.css";
import {getDashboardData} from "@/api";
import dayjs from "dayjs";
type Props={
  items: ConsultantItemsType[],
  groups: ConsultantsCalendarType[]
}
const GanttChart = ({items, groups}:Props) => {
  console.log('ganttchart items', items)
  console.log('ganttchart groups', groups)
  const client = useClientsContext();
  const idParam = usePathname().split("/").pop();
  const singleClient = client.data.filter((el) => el.id === idParam)[0];
  // const [items, setItems] = useState<ConsultantItemsType[]>(consultantItems);
  // const [groups, setGroups] = useState([])
  // useEffect(() => {
  //   const remainingTimeItem = getDatesForRemainingTime(1, items);
  //   const newItem = {
  //     id: 20,
  //     group: 1,
  //     start_time: remainingTimeItem.start_date,
  //     end_time: remainingTimeItem.end_date,
  //     itemProps: {
  //       style: {
  //         zIndex: 1,
  //         backgroundColor: "grey",
  //       },
  //     },
  //   };
  //   const pushedItem = [...items, newItem];
  //   setItems(pushedItem);
  // }, []);

  //
  // useEffect(() => {
  //   fetch("http://localhost:8080/api/consultants")
  //     .then(res=> res.json())
  //     .then(res => {
  //       const data = res.consultants.map(el => {
  //         return el.registeredTimeDtoList.map(item => {
  //           return {
  //             id: item.registeredTimeId,
  //             group: el.id,
  //             start_time: dayjs(item.startDate),
  //             end_time: dayjs(item.endDate),
  //             // style: {
  //             //   zIndex: 1,
  //             //   background: selectColor(item.type),
  //             //   outline: "none",
  //             //   border: "none"
  //             // },
  //           }
  //         })
  //       })
  //       setItems(data);
  //       return res;
  //     })
  //     .then(res => {
  //       const data = res.consultants.map(el => {
  //         return {id: el.id, title: el.fullName}
  //       })
  //       setGroups(data);
  //     })
  // }, []);
  // const handleTimeChange = (visibleTimeStart, visibleTimeEnd, updateScrollCanvas) => {
  //   updateScrollCanvas(moment(this.state.defaultTimeStart).valueOf(),
  //     moment(this.state.defaultTimeEnd).valueOf());
  // }

  return (
    <div>
        <div>
            <Timeline
                groups={groups}
                items={items}
                // buffer={5}
                // onTimeChange={handleTimeChange}
                defaultTimeStart={moment().add(-17, "day")}
                defaultTimeEnd={moment().add(4, "day")}
            />
        </div>
      {/*{singleClient.listOfConsultants.map((consultant) => {*/}
      {/*  const { name, id } = consultant;*/}
      {/*  return <SingleConsultant name={name} id={id} key={id} />;*/}
      {/*})}*/}
    </div>
  );
};

export default GanttChart;
