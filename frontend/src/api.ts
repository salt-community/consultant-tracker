export const getConsultantsData = async (page, pageSize, clientEncodeURI, ptsEncodeURI, filterName) => {
  return await fetch(
    `http://localhost:8080/api/consultants?page=${page}&pageSize=${pageSize}&${ptsEncodeURI}&${clientEncodeURI}&name=${filterName}`)
    .then((response) => response.json())
}

export const getRedDays = async () => {
  return await fetch("http://localhost:8080/api/redDays")
    .then((response) => response.json());
}

export const getConsultantById = async (id: string) => {
  return await fetch(`http://localhost:8080/api/consultants/${id}`)
    .then((response) => response.json());
}

export const getInfographicsByPt = async (pt: string) => {
  return await fetch(`http://localhost:8080/api/consultants/infographics/${pt}`)
    .then((response) => response.json());
}