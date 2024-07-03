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
  },
  { id: "002", name: "Astrad Rehnquist", client: "Scania", status: "absence" },
  {
    id: "003",
    name: "Josephine Ahlgren Björk",
    client: "Astra Zeneca",
    status: "absence",
  },
  { id: "004", name: "Olof Dahlquist", client: "", status: "inactive" },
  { id: "005", name: "Axel Folly", client: "Spotify", status: "active" },
  { id: "006", name: "Allan Sanddahl", client: "ABB", status: "active" },
  { id: "007", name: "Julia Roberts", client: "Telia", status: "active" },
  { id: "008", name: "Agnes Ekbom", client: "Scania", status: "absence" },
  { id: "009", name: "Zara Larsson", client: "", status: "inactive" },
  { id: "010", name: "Iris Lindahl", client: "Swedbank", status: "active" },
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
