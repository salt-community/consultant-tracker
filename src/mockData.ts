import { ConsultantDataType, ConsultantDetailsDataType, HeaderCellsType } from "./types";

export const navLinks = [
  "Create new consultant",
  "Create new client",
  "Sign out",
];

export const infographicData = [
  { title: "Total consultants", amount: 10, variant: "blue" },
  { title: "Active consultants", amount: 7, variant: "green" },
  { title: "Absent consultants", amount: 3, variant: "yellow" },
  { title: "Number of clients", amount: 7, variant: "violet" },
];

export const consultantsData = [
  {
    id: "001",
    name: "Stefan Hansdotter",
    client: "Syntronix",
    status: "active",
    details: "details.svg",
  },
  {
    id: "002",
    name: "Astrad Rehnquist",
    client: "Scania",
    status: "absence",
    details: "details.svg",
  },
  {
    id: "003",
    name: "Josephine Ahlgren Björk",
    client: "Astra Zeneca",
    status: "absence",
    details: "details.svg",
  },
  {
    id: "004",
    name: "Olof Dahlquist",
    client: "-",
    status: "inactive",
    details: "details.svg",
  },
  {
    id: "005",
    name: "Axel Folly",
    client: "Spotify",
    status: "active",
    details: "details.svg",
  },
  {
    id: "006",
    name: "Allan Sanddahl",
    client: "ABB",
    status: "active",
    details: "details.svg",
  },
  {
    id: "007",
    name: "Julia Roberts",
    client: "Telia",
    status: "active",
    details: "details.svg",
  },
  {
    id: "008",
    name: "Agnes Ekbom",
    client: "Scania",
    status: "absence",
    details: "details.svg",
  },
  {
    id: "009",
    name: "Zara Larsson",
    client: "-",
    status: "inactive",
    details: "details.svg",
  },
  {
    id: "010",
    name: "Iris Lindahl",
    client: "Swedbank",
    status: "active",
    details: "details.svg",
  },
];

export const statusOptions = ["Show All", "Active", "Absence", "Inactive"];

export const status = [
  {
    id: 1,
    value: "active",
  },
  {
    id: 2,
    value: "absence",
  },
  {
    id: 3,
    value: "inactive",
  },
];

export const headCells: HeaderCellsType[] = [
  {
    disablePadding: false,
    id: "status",
    label: "Status",
    numeric: false,
  },
  {
    disablePadding: false,
    id: "name",
    label: "Name",
    numeric: false,
  },
  {
    disablePadding: false,
    id: "client",
    label: "Client",
    numeric: false,
  },
  {
    disablePadding: false,
    id: "details",
    label: "Details",
    numeric: false,
  },
];

export const consultantDetailsData: ConsultantDetailsDataType[] = [
  {
    id: "001",
    name: "Stefan Hansdotter",
    client: [
      {
        id: "001",
        name: "Syntronix",
        startDate: "2021-01-01",
        endDate: "2021-12-31",
      },
    ],
    status: "active",
    absence: [],
    address: "Kungsgatan 1, 111 11 Stockholm",
    phone: "070-123 45 67",
    startDate: "2021-01-01",
    remainsHours: 200,
    email: "voldemort_2004@hotmail.com",
    meetings: [
      {
        id: "001",
        date: "2021-02-01",
        title: "First regular meeting",
        description: "Ask something",
      },
      {
        id: "002",
        date: "2021-02-01",
        title: "Second regular meeting",
        description: "Ask something",
      },
      {
        id: "003",
        date: "2021-02-01",
        title: "Third regular meeting",
        description: "Ask something",
      },
    ],
  },
  {
    id: "002",
    name: "Astrad Rehnquist",
    client: [
      {
        id: "002",
        name: "Scania",
        startDate: "2021-01-01",
        endDate: "2021-12-31",
      },
    ],
    status: "absence",
    absence: [
      {
        description: "Parental leave",
        startDateAbsence: "2021-01-01",
        endDateAbsence: "2021-01-10",
        absenceHours: 80,
      },
    ],
    address: "Kungsgatan 2, 111 11 Stockholm",
    phone: "070-123 45 67",
    startDate: "2021-01-01",
    remainsHours: 200,
    email: "jarry_porter_2004@hotmail.com",
    meetings: [
      {
        id: "001",
        date: "2021-02-01",
        title: "First regular meeting",
        description: "Ask something",
      },
      {
        id: "002",
        date: "2021-02-01",
        title: "Second regular meeting",
        description: "Ask something",
      },
      {
        id: "003",
        date: "2021-02-01",
        title: "Third regular meeting",
        description: "Ask something",
      },
    ],
  },
  {
    id: "003",
    name: "Josephine Ahlgren Björk",
    client: [
      {
        id: "003",
        name: "AstraZeneca",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    status: "absence",
    absence: [
      {
        description: "Vacation",
        startDateAbsence: "2021-01-01",
        endDateAbsence: "2021-01-10",
        absenceHours: 80,
      },
    ],
    address: "Kungsgatan 2, 111 11 Stockholm",
    phone: "070-123 45 67",
    startDate: "2021-01-01",
    remainsHours: 200,
    email: "voldemort_2004@hotmail.com",
    meetings: [
      {
        id: "001",
        date: "2021-02-01",
        title: "First regular meeting",
        description: "Ask something",
      },
      {
        id: "002",
        date: "2021-02-01",
        title: "Second regular meeting",
        description: "Ask something",
      },
      {
        id: "003",
        date: "2021-02-01",
        title: "Third regular meeting",
        description: "Ask something",
      },
    ],
  },
  {
    id: "004",
    name: "Olof Dahlquist",
    client: [],
    status: "inactive",
    absence: [],
    address: "Kungsgatan 3, 111 11 Stockholm",
    phone: "070-223 45 67",
    startDate: "2023-01-01",
    remainsHours: 100,
    email: "the_lord_of_the_all_rings_44_22@hotmail.com",
    meetings: [
      {
        id: "001",
        date: "2021-02-01",
        title: "First regular meeting",
        description: "Ask something",
      },
      {
        id: "002",
        date: "2021-02-01",
        title: "Second regular meeting",
        description: "Ask something",
      },
      {
        id: "003",
        date: "2021-02-01",
        title: "Third regular meeting",
        description: "Ask something",
      },
    ],
  },
  {
    id: "005",
    name: "Axel Folly",
    client: [
      {
        id: "004",
        name: "Spotify",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    status: "active",
    absence: [],
    address: "Kungsgatan 5, 111 11 Stockholm",
    phone: "070-113 45 67",
    startDate: "2022-01-01",
    remainsHours: 10,
    email: "the_@hotmail.com",
    meetings: [
      {
        id: "001",
        date: "2021-02-01",
        title: "First regular meeting",
        description: "Ask something",
      },
      {
        id: "002",
        date: "2021-02-01",
        title: "Second regular meeting",
        description: "Ask something",
      },
      {
        id: "003",
        date: "2021-02-01",
        title: "Third regular meeting",
        description: "Ask something",
      },
    ],
  },
  {
    id: "006",
    name: "Allan Sanddahl",
    client: [
      {
        id: "005",
        name: "ABB",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    status: "active",
    absence: [],
    address: "Kungsgatan 10, 111 11 Stockholm",
    phone: "070-113 45 67",
    startDate: "2022-03-01",
    remainsHours: 999,
    email: "the_allan@hotmail.com",
    meetings: [
      {
        id: "001",
        date: "2021-02-01",
        title: "First regular meeting",
        description: "Ask something",
      },
      {
        id: "002",
        date: "2021-02-01",
        title: "Second regular meeting",
        description: "Ask something",
      },
      {
        id: "003",
        date: "2021-02-01",
        title: "Third regular meeting",
        description: "Ask something",
      },
    ],
  },
  {
    id: "007",
    name: "Julia Roberts",
    client: [
      {
        id: "006",
        name: "Telia",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    status: "active",
    absence: [],
    address: "Kungsgatan 11, 111 11 Stockholm",
    phone: "070-113 45 67",
    startDate: "2023-03-02",
    remainsHours: 999,
    email: "the_pretty_woman@hotmail.com",
    meetings: [
      {
        id: "001",
        date: "2021-02-01",
        title: "First regular meeting",
        description: "Ask something",
      },
      {
        id: "002",
        date: "2021-02-01",
        title: "Second regular meeting",
        description: "Ask something",
      },
      {
        id: "003",
        date: "2021-02-01",
        title: "Third regular meeting",
        description: "Ask something",
      },
    ],
  },
  {
    id: "008",
    name: "Agnes Ekbom",
    client: [
      {
        id: "001",
        name: "Scania",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    status: "absence",
    absence: [
      {
        description: "Sick leave",
        startDateAbsence: "2024-01-01",
      },
    ],
    address: "Kungsgatan 11, 111 11 Stockholm",
    phone: "070-113 45 67",
    startDate: "2023-03-02",
    remainsHours: 999,
    email: "the_pretty_ekbom@hotmail.com",
    meetings: [
      {
        id: "001",
        date: "2021-02-01",
        title: "First regular meeting",
        description: "Ask something",
      },
      {
        id: "002",
        date: "2021-02-01",
        title: "Second regular meeting",
        description: "Ask something",
      },
      {
        id: "003",
        date: "2021-02-01",
        title: "Third regular meeting",
        description: "Ask something",
      },
    ],
  },
  {
    id: "009",
    name: "Zara Larsson",
    client: [],
    status: "inactive",
    absence: [],
    address: "Kungsgatan 20, 111 11 Stockholm",
    phone: "070-113 45 67",
    startDate: "2023-03-02",
    remainsHours: 109,
    email: "less_famous_than_avici@hotmail.com",
    meetings: [
      {
        id: "001",
        date: "2021-02-01",
        title: "First regular meeting",
        description: "Ask something",
      },
      {
        id: "002",
        date: "2021-02-01",
        title: "Second regular meeting",
        description: "Ask something",
      },
      {
        id: "003",
        date: "2021-02-01",
        title: "Third regular meeting",
        description: "Ask something",
      },
    ],
  },
  {
    id: "010",
    name: "Iris Lindahl",
    client: [
      {
        id: "007",
        name: "Swedbank",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    status: "active",
    absence: [],
    address: "Kungsgatan 100, 111 11 Stockholm",
    phone: "070-113 45 67",
    startDate: "2022-03-02",
    remainsHours: 101,
    email: "the-iris@hotmail.com",
    meetings: [
      {
        id: "001",
        date: "2021-02-01",
        title: "First regular meeting",
        description: "Ask something",
      },
      {
        id: "002",
        date: "2021-02-01",
        title: "Second regular meeting",
        description: "Ask something",
      },
      {
        id: "003",
        date: "2021-02-01",
        title: "Third regular meeting",
        description: "Ask something",
      },
    ],
  },
];
