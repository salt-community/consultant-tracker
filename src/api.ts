const BASE_URL = import.meta.env.VITE_BACKEND_URL;

export const getConsultantsData = async (page:number, pageSize:number, clientEncodeURI:string, ptsEncodeURI:string, filterName:string, includePgps: boolean) => {
  return await fetch(
    `${BASE_URL}/api/consultants?page=${page}&pageSize=${pageSize}&${ptsEncodeURI}&${clientEncodeURI}&name=${filterName}&includePgps=${includePgps}`)
    .then((response) => response.json());
}

export const getRedDays = async () => {
  return await fetch(`${BASE_URL}/api/redDays`)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }
      return response.json()
    });
}

export const getConsultantById = async (id: string) => {
  return await fetch(`${BASE_URL}/api/consultants/${id}`)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }
      return response.json()
    });
}

export const getInfographicsByPt = async (pt: string) => {
  return await fetch(`${BASE_URL}/api/consultants/infographics/${pt}`)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }
      return response.json()
    });
}

export const getAllClientsAndPts = async (includePgps: boolean) => {
  return await fetch(`${BASE_URL}/api/consultants/getAllClientsAndPts?includePgps=${includePgps}`)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }
      return response.json()
    })
}