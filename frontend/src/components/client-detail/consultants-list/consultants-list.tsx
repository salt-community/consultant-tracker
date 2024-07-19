import { useClientsContext } from "@/context/clients";
import SingleConsultant from "@/components/client-detail/consultants-list/single-consultant/single-consultant";
import { usePathname } from "next/navigation";
import Timeline from "react-calendar-timeline";
import "react-calendar-timeline/lib/Timeline.css";
import moment from "moment";
import { consultantDataCalendar, consultantItems } from "@/mockData";

const groups = [
  { id: 1, title: "group 1" },
  { id: 2, title: "group 2" },
];

const ConsultantsList = () => {
  const items = [
    {
      id: 1,
      group: 1,
      title: "item 1",
      start_time: moment(),
      end_time: moment().add(1, "hour"),
    },
    {
      id: 2,
      group: 2,
      title: "item 2",
      start_time: moment().add(-0.5, "hour"),
      end_time: moment().add(0.5, "hour"),
    },
    {
      id: 3,
      group: 1,
      title: "item 3",
      start_time: moment().add(2, "hour"),
      end_time: moment().add(3, "hour"),
    },
  ];

  const client = useClientsContext();
  const idParam = usePathname().split("/").pop();
  const singleClient = client.data.filter((el) => el.id === idParam)[0];


  return (
    <div>
      <div>
        <Timeline
          groups={consultantDataCalendar}
          items={consultantItems}
          defaultTimeStart={moment().add(-12, "hour")}
          defaultTimeEnd={moment().add(12, "hour")}
        
        />
      </div>
      {singleClient.listOfConsultants.map((consultant) => {
        const { name, id } = consultant;
        return <SingleConsultant name={name} id={id} key={id} />;
      })}
    </div>
  );
};

export default ConsultantsList;
