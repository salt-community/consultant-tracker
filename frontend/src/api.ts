export const getConsultantsData = async (page:number, pageSize:number, clientEncodeURI:string, ptsEncodeURI:string, filterName:string) => {
  return await fetch(
    `http://localhost:8080/api/consultants?page=${page}&pageSize=${pageSize}&${ptsEncodeURI}&${clientEncodeURI}&name=${filterName}`)
    .then((response) => response.json());
}

export const getRedDays = async () => {
  return await fetch("http://localhost:8080/api/redDays")
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }
      return response.json()
    });
}

export const getConsultantById = async (id: string) => {
  return await fetch(`http://localhost:8080/api/consultants/${id}`)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }
      return response.json()
    });
}

export const getInfographicsByPt = async (pt: string) => {
  return await fetch(`http://localhost:8080/api/consultants/infographics/${pt}`)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }
      return response.json()
    });
}

export const getAllClientsAndPts = async () => {
  return await fetch("http://localhost:8080/api/consultants/getAllClientsAndPts")
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }
      return response.json()
    })
}