

// import dayjs from "dayjs";
import {
  ClientsDetailsDataType,
  // ConsultantDataType,
  ConsultantDetailsDataType,
  // ConsultantItemsType,
  // ConsultantsCalendarType,
  // HeaderCellsType,
} from "./types";

export const navLinks = [
  "Sign out",
];


export const tabs = [
  {
    id: 1,
    label: "Personal Data",
    value: "personalData",
  },
  {
    id: 2,
    label: "Meetings schedule",
    value: "schedule",
  },
  {
    id: 3,
    label: "Absences",
    value: "absences",
  },
  {
    id: 4,
    label: "Clients",
    value: "clients",
  },
];

// export const consultantsData: ConsultantDataType[] = [
//   {
//     id: "001",
//     fullName: "Stefan Hansdotter",
//     clientId: "111",
//     client: "Syntronix",
//     pt: "Josefin Stål"
//   },
//   {
//     id: "002",
//     fullName: "Astrad Rehnquist",
//     clientId: "222",
//     client: "Scania",
//     pt: "Josefin Stål"
//   },
//   {
//     id: "003",
//     fullName: "Josephine Ahlgren Björk",
//     clientId: "333",
//     client: "AstraZeneca",
//     pt: "Josefin Stål"
//   },
//   {
//     id: "004",
//     fullName: "Olof Dahlquist",
//     clientId: "-",
//     client: "-",
//     pt: "Josefin Stål"
//   },
//   {
//     id: "005",
//     fullName: "Axel Folly",
//     clientId: "444",
//     client: "Spotify",
//     pt: "Josefin Stål"
//   },
//   {
//     id: "006",
//     fullName: "Allan Sanddahl",
//     clientId: "555",
//     client: "ABB",
//     pt: "Josefin Stål"
//   },
//   {
//     id: "007",
//     fullName: "Julia Roberts",
//     clientId: "666",
//     client: "Telia",
//     pt: "Anna Carlsson"
//   },
//   {
//     id: "008",
//     fullName: "Agnes Ekbom",
//     clientId: "222",
//     client: "Scania",
//     pt: "Anna Carlsson"
//   },
//   {
//     id: "009",
//     fullName: "Zara Larsson",
//     clientId: "-",
//     client: "-",
//     pt: "Anna Carlsson"
//   },
//   {
//     id: "010",
//     fullName: "Iris Lindahl",
//     clientId: "777",
//     client: "Swedbank",
//     pt: "Anna Carlsson"
//   },
// ];

// export const headCells: HeaderCellsType[] = [
//   {
//     id: "pt",
//     label: "Responsible P&T",
//   },
//   {
//     id: "name",
//     label: "Name",
//   },
//   {
//     id: "client",
//     label: "Client",
//   },
// ];

export const consultantDetailsData: ConsultantDetailsDataType[] = [
  {
    id: "001",
    name: "Stefan Hansdotter",
    client: [
      {
        // id: "111",
        name: "Syntronix",
        startDate: "2021-01-01",
        endDate: "2021-12-31",
      },
    ],
    pt: "active",
    absence: [],
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
        // id: "222",
        name: "Scania",
        startDate: "2021-01-01",
        endDate: "2021-12-31",
      },
    ],
    pt: "absence",
    absence: [
      {
        description: "Parental leave",
        startDateAbsence: "2021-01-01",
        endDateAbsence: "2021-01-10",
        absenceHours: 80,
      },
    ],
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
        // id: "333",
        name: "AstraZeneca",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    pt: "absence",
    absence: [
      {
        description: "Vacation",
        startDateAbsence: "2021-01-01",
        endDateAbsence: "2021-01-10",
        absenceHours: 80,
      },
    ],
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
    pt: "inactive",
    absence: [],
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
        // id: "444",
        name: "Spotify",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    pt: "active",
    absence: [],
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
        // id: "555",
        name: "ABB",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    pt: "active",
    absence: [],
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
        // id: "666",
        name: "Telia",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    pt: "active",
    absence: [],
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
        // id: "222",
        name: "Scania",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    pt: "absence",
    absence: [
      {
        description: "Sick leave",
        startDateAbsence: "2024-01-01",
      },
    ],
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
    pt: "inactive",
    absence: [],
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
        // id: "777",
        name: "Swedbank",
        startDate: "2022-01-01",
        endDate: "2022-12-31",
      },
    ],
    pt: "active",
    absence: [],
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


// export const clientsData: ClientsDetailsDataType[] = [
//   {
//     id: "111",
//     fullName: "Syntronix",
//     contactPeople: [
//       {
//         id: "11",
//         fullName: "Edvin Ekdal",
//         phone: "111-222-111",
//         email: "edvin.ekdal@syntronix.se",
//       },
//     ],
//     listOfConsultants: [
//       {
//         id: "001",
//         fullName: "Stefan Hansdotter",
//       },
//     ],
//   },
//   {
//     id: "222",
//     fullName: "Scania",
//     contactPeople: [
//       {
//         id: "22",
//         fullName: "Leon Göransson",
//         phone: "111-222-222",
//         email: "leon.goransson@scania.se",
//       },
//       {
//         id: "33",
//         fullName: "Thor Söderberg",
//         phone: "111-222-223",
//         email: "thor.soderberg@scania.se",
//       },
//     ],
//     listOfConsultants: [
//       {
//         id: "002",
//         fullName: "Astrad Rehnquist",
//       },
//       {
//         id: "008",
//         fullName: "Agnes Ekbom",
//       },
//     ],
//   },
//   {
//     id: "333",
//     fullName: "AstraZeneca",
//     contactPeople: [
//       {
//         id: "44",
//         fullName: "Thomas Lundmark",
//         phone: "111-222-333",
//         email: "thomas.lundmark@astrazeneca.se",
//       },
//     ],
//     listOfConsultants: [
//       {
//         id: "003",
//         fullName: "Josephine Ahlgren Björk",
//       },
//     ],
//   },
//   {
//     id: "444",
//     fullName: "Spotify",
//     contactPeople: [
//       {
//         id: "55",
//         fullName: "Loke Lundholm",
//         phone: "111-222-444",
//         email: "loke.lundholm@spotify.se",
//       },
//     ],
//     listOfConsultants: [
//       {
//         id: "005",
//         fullName: "Axel Folly",
//       },
//     ],
//   },
//   {
//     id: "555",
//     fullName: "ABB",
//     contactPeople: [
//       {
//         id: "66",
//         fullName: "Odert Haglund",
//         phone: "111-222-555",
//         email: "odert.haglund@abb.se",
//       },
//     ],
//     listOfConsultants: [
//       {
//         id: "006",
//         fullName: "Allan Sanddahl",
//       },
//     ],
//   },
//   {
//     id: "666",
//     fullName: "Telia",
//     contactPeople: [
//       {
//         id: "77",
//         fullName: "Lars Persson",
//         phone: "111-222-666",
//         email: "lars.persson@telia.se",
//       },
//     ],
//     listOfConsultants: [
//       {
//         id: "007",
//         fullName: "Julia Roberts",
//       },
//     ],
//   },
//   {
//     id: "777",
//     fullName: "Swedbank",
//     contactPeople: [
//       {
//         id: "88",
//         fullName: "Axel Westermark",
//         phone: "111-222-777",
//         email: "axel.westermark@swedbank.se",
//       },
//     ],
//     listOfConsultants: [
//       {
//         id: "010",
//         fullName: "Iris Lindahl",
//       },
//     ],
//   },
// ];

// export const consultantsCalendar: ConsultantsCalendarType[] = [
//   {id: 1, title: "Stefan Hansdotter"},
//   {id: 2, title: "Astrad Rehnquist"},
//   {id: 3, title: "Josephine Ahlgren Björk"},
//   {id: 4, title: "Olof Dahlquist"},
//   {id: 5, title: "Axel Folly"},
//   {id: 6, title: "Allan Sanddahl"},
//   {id: 7, title: "Julia Roberts"},
//   {id: 8, title: "Agnes Ekbom"},
//   {id: 9, title: "Zara Larsson"},
//   {id: 10, title: "Iris Lindahl"},
// ];

// export const consultantItems: ConsultantItemsType[] = [
//   {
//     id: 1,
//     group: 6,
//     start_time: dayjs("2024-07-22T00:00:00"),
//     end_time: dayjs("2024-08-22T23:59:59"),
//     itemProps: {
//       style: {
//         zIndex: 1,
//         background: "#29B269",
//         outline: "none",
//         border: "none"
//       },
//     },
//   },
//   {
//     id: 2,
//     group: 6,
//     start_time: dayjs("2024-08-05T00:00:00"),
//     end_time: dayjs("2024-08-09T23:59:59"),
//     itemProps: {
//       style: {
//         zIndex: 2,
//         background: "yellow",
//         outline: "none",
//         border: "none"
//       },
//     },
//   },
//   {
//     id: 3,
//     group: 6,
//     start_time: dayjs("2024-08-09T00:00:00"),
//     end_time: dayjs("2025-05-09T23:59:59"),
//     itemProps: {
//       style: {
//         zIndex: 0,
//         background: "#A2A2A2",
//         outline: "none",
//         border: "none"
//       },
//     },
//   }
// ];

// export const redDays = [
//   {
//     year: 2024,
//     redDays: [
//       "2024-01-01",
//       // "2024-01-06",
//       "2024-03-29",
//       // "2024-03-31",
//       "2024-04-01",
//       "2024-05-01",
//       "2024-05-09",
//       // "2024-05-19",
//       "2024-06-06",
//       // "2024-06-22",
//       // "2024-11-02",
//       "2024-12-25",
//       "2024-12-26"
//     ]
//   },
//   {
//     year: 2025,
//     redDays: [
//       "2025-01-01",
//       "2025-01-06",
//       "2025-04-18",
//       // "2025-04-20",
//       "2025-04-21",
//       "2025-05-01",
//       "2025-05-29",
//       "2025-06-06",
//       // "2025-06-08",
//       // "2025-06-21",
//       // "2025-11-01",
//       "2025-12-25",
//       "2025-12-26"
//     ]
//   }
// ]

// export const allPts = ["Anna Carlsson", "Josefin Stål"];