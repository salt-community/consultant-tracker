import {
  ClientsDetailsDataType,
  ConsultantDataType,
  ConsultantDetailsDataType,
  ConsultantItemsType,
  ConsultantsCalendarType,
  HeaderCellsType,
} from "./types";

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

export const consultantsData: ConsultantDataType[] = [
  {
    id: "001",
    name: "Stefan Hansdotter",
    clientId: "111",
    client: "Syntronix",
    status: "active",
  },
  {
    id: "002",
    name: "Astrad Rehnquist",
    clientId: "222",
    client: "Scania",
    status: "absence",
  },
  {
    id: "003",
    name: "Josephine Ahlgren Björk",
    clientId: "333",
    client: "AstraZeneca",
    status: "absence",
  },
  {
    id: "004",
    name: "Olof Dahlquist",
    clientId: "-",
    client: "-",
    status: "inactive",
  },
  {
    id: "005",
    name: "Axel Folly",
    clientId: "444",
    client: "Spotify",
    status: "active",
  },
  {
    id: "006",
    name: "Allan Sanddahl",
    clientId: "555",
    client: "ABB",
    status: "active",
  },
  {
    id: "007",
    name: "Julia Roberts",
    clientId: "666",
    client: "Telia",
    status: "active",
  },
  {
    id: "008",
    name: "Agnes Ekbom",
    clientId: "222",
    client: "Scania",
    status: "absence",
  },
  {
    id: "009",
    name: "Zara Larsson",
    clientId: "-",
    client: "-",
    status: "inactive",
  },
  {
    id: "010",
    name: "Iris Lindahl",
    clientId: "777",
    client: "Swedbank",
    status: "active",
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
    id: "status",
    label: "Status",
  },
  {
    id: "name",
    label: "Name",
  },
  {
    id: "client",
    label: "Client",
  },
];

export const consultantDetailsData: ConsultantDetailsDataType[] = [
  {
    id: "001",
    name: "Stefan Hansdotter",
    client: [
      {
        id: "111",
        name: "Syntronix",
        startDate: "2021-01-01",
        endDate: "2021-12-31",
      },
    ],
    status: "active",
    absence: [],
    github: "stefan67509",
    phone: "070-123 45 67",
    startDate: "2021-01-01",
    remainingHours: 200,
    email: "stefan.hansdotter@appliedtechnology.se",
    meetings: [
      {
        id: "001",
        date: "2021-02-01",
        title: "First regular meeting",
        description:
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
      },
      {
        id: "002",
        date: "2021-02-01",
        title: "Second regular meeting",
        description:
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
      },
      {
        id: "003",
        date: "2021-02-01",
        title: "Third regular meeting",
        description:
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
      },
    ],
  },
  {
    id: "002",
    name: "Astrad Rehnquist",
    client: [
      {
        id: "222",
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
    github: "astradthereh",
    phone: "070-123 45 67",
    startDate: "2021-01-01",
    remainingHours: 200,
    email: "astrad.rehnquist@appliedtechnology.se",
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
        id: "333",
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
    github: "jojophin",
    phone: "070-123 45 67",
    startDate: "2021-01-01",
    remainingHours: 200,
    email: "josephine.ahlgren.bjork@appliedtechnology.se",
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
    github: "olofdahlquist",
    phone: "070-223 45 67",
    startDate: "2023-01-01",
    remainingHours: 100,
    email: "olof.dahlquist@appliedtechnology.se",
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
        id: "444",
        name: "Spotify",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    status: "active",
    absence: [],
    github: "axelfolly",
    phone: "070-113 45 67",
    startDate: "2022-01-01",
    remainingHours: 10,
    email: "axel.folly@appliedtechnology.se",
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
        id: "555",
        name: "ABB",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    status: "active",
    absence: [],
    github: "sanddahlall",
    phone: "070-113 45 67",
    startDate: "2022-03-01",
    remainingHours: 999,
    email: "allan.sanddahl@appliedtechnology.se",
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
        id: "666",
        name: "Telia",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    status: "active",
    absence: [],
    github: "juliaroberts",
    phone: "070-113 45 67",
    startDate: "2023-03-02",
    remainingHours: 999,
    email: "julia.roberts@appliedtechnology.se",
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
        id: "222",
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
    github: "agnesekbom",
    phone: "070-113 45 67",
    startDate: "2023-03-02",
    remainingHours: 999,
    email: "agnes.ekbom@appliedtechnology.se",
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
    github: "zaralarsson",
    phone: "070-113 45 67",
    startDate: "2023-03-02",
    remainingHours: 109,
    email: "zara.larsson@appliedtechnology.se",
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
        id: "777",
        name: "Swedbank",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    status: "active",
    absence: [],
    github: "irislinda",
    phone: "070-113 45 67",
    startDate: "2022-03-02",
    remainingHours: 101,
    email: "iris.lindahl@appliedtechnology.se",
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

export const tabs = [
  {
    id: 1,
    label: "Clients",
    value: "clients.tsx",
  },
  {
    id: 2,
    label: "Meetings schedule",
    value: "schedule",
  },
  {
    id: 3,
    label: "Vacation",
    value: "vacation",
  },
];

export const clientsData: ClientsDetailsDataType[] = [
  {
    id: "111",
    name: "Syntronix",
    contactPeople: [
      {
        id: "11",
        name: "Edvin Ekdal",
        phone: "111-222-111",
        email: "edvin.ekdal@syntronix.se",
      },
    ],
    listOfConsultants: [
      {
        id: "001",
        name: "Stefan Hansdotter",
      },
    ],
  },
  {
    id: "222",
    name: "Scania",
    contactPeople: [
      {
        id: "22",
        name: "Leon Göransson",
        phone: "111-222-222",
        email: "leon.goransson@scania.se",
      },
      {
        id: "33",
        name: "Thor Söderberg",
        phone: "111-222-223",
        email: "thor.soderberg@scania.se",
      },
    ],
    listOfConsultants: [
      {
        id: "002",
        name: "Astrad Rehnquist",
      },
      {
        id: "008",
        name: "Agnes Ekbom",
      },
    ],
  },
  {
    id: "333",
    name: "AstraZeneca",
    contactPeople: [
      {
        id: "44",
        name: "Thomas Lundmark",
        phone: "111-222-333",
        email: "thomas.lundmark@astrazeneca.se",
      },
    ],
    listOfConsultants: [
      {
        id: "003",
        name: "Josephine Ahlgren Björk",
      },
    ],
  },
  {
    id: "444",
    name: "Spotify",
    contactPeople: [
      {
        id: "55",
        name: "Loke Lundholm",
        phone: "111-222-444",
        email: "loke.lundholm@spotify.se",
      },
    ],
    listOfConsultants: [
      {
        id: "005",
        name: "Axel Folly",
      },
    ],
  },
  {
    id: "555",
    name: "ABB",
    contactPeople: [
      {
        id: "66",
        name: "Odert Haglund",
        phone: "111-222-555",
        email: "odert.haglund@abb.se",
      },
    ],
    listOfConsultants: [
      {
        id: "006",
        name: "Allan Sanddahl",
      },
    ],
  },
  {
    id: "666",
    name: "Telia",
    contactPeople: [
      {
        id: "77",
        name: "Lars Persson",
        phone: "111-222-666",
        email: "lars.persson@telia.se",
      },
    ],
    listOfConsultants: [
      {
        id: "007",
        name: "Julia Roberts",
      },
    ],
  },
  {
    id: "777",
    name: "Swedbank",
    contactPeople: [
      {
        id: "88",
        name: "Axel Westermark",
        phone: "111-222-777",
        email: "axel.westermark@swedbank.se",
      },
    ],
    listOfConsultants: [
      {
        id: "010",
        name: "Iris Lindahl",
      },
    ],
  },
];

export const consultantsCalendar: ConsultantsCalendarType[] = [
  { id: 1, title: "Stefan Hansdotter" },
  { id: 2, title: "Astrad Rehnquist" },
  { id: 3, title: "Josephine Ahlgren Björk" },
  { id: 4, title: "Olof Dahlquist" },
  { id: 5, title: "Axel Folly" },
  { id: 6, title: "Allan Sanddahl" },
  { id: 7, title: "Julia Roberts" },
  { id: 8, title: "Agnes Ekbom" },
  { id: 9, title: "Zara Larsson" },
  { id: 10, title: "Iris Lindahl" },
];

export const consultantItems: ConsultantItemsType[] = [
  {
    id: 1,
    group: 1,
    start_time: new Date("2024-07-17T00:00:00"),
    end_time: new Date("2024-07-17T23:59:59"),
    // itemProps: {
    //   style: {
    //     backgroundColor: "red",
    //   },
    // },
  },
  {
    id: 2,
    group: 2,
    start_time: new Date("2024-07-17T00:00:00"),
    end_time: new Date("2024-07-17T23:59:59"),
  },
  {
    id: 3,
    group: 1,
    start_time: new Date("2024-07-18T00:00:00"),
    end_time: new Date("2024-07-18T23:59:59"),
    itemProps: {
      style: {
        backgroundColor: "green",
      },
    },
  },
  {
    id: 4,
    group: 2,
    start_time: new Date("2024-07-18T00:00:00"),
    end_time: new Date("2024-07-18T23:59:59"),
  },
  {
    id: 5,
    group: 1,
    start_time: new Date("2024-07-19T00:00:00"),
    end_time: new Date("2024-07-19T23:59:59"),
  },
  {
    id: 6,
    group: 1,
    start_time: new Date("2024-07-22T00:00:00"),
    end_time: new Date("2024-07-22T23:59:59"),
  },
  // {
  //   id: 7,
  //   group: 1,
  //   start_time: new Date("2024-05-01T00:00:00"),
  //   end_time: new Date("2024-07-16T23:59:59"),
  //   itemProps: {
  //     style: {
  //       backgroundColor: "grey",
  //     },
  //   },
  // },
];
