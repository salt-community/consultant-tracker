const BASE_URL = import.meta.env.VITE_BACKEND_URL;

export const getConsultantsData = async (
  page: number,
  pageSize: number,
  clientEncodeURI: string,
  ptsEncodeURI: string,
  filterName: string,
  includePgps: boolean,
  token: string
) => {
  return await fetch(
    `${BASE_URL}/api/consultants?page=${page}&pageSize=${pageSize}&${ptsEncodeURI}&${clientEncodeURI}&name=${filterName}&includePgps=${includePgps}`,
    {
      headers: {
        Authorization: `Bearer ${token}`
      },
    }
  )
    .then((response) => response.json())
    .catch((err) => console.log("consultant fetch err", err));
};

export const getRedDays = async (token: string) => {
  return await fetch(`${BASE_URL}/api/redDays`,
    {
      headers: {
        Authorization: `Bearer ${token}`
      },
    }
  ).then((response) => {
    if (!response.ok) {
      throw new Error(`HTTP error ${response.status}`);
    }
    return response.json();
  });
};

export const getConsultantById = async (id: string, token: string) => {
  return await fetch(`${BASE_URL}/api/consultants/${id}`,
    {
      headers: {
        Authorization: `Bearer ${token}`
      },
    }).then((response) => {
    if (!response.ok) {
      throw new Error(`HTTP error ${response.status}`);
    }
    return response.json();
  });
};

export const getInfographicsByPt = async (pt: string, token: string) => {
  return await fetch(`${BASE_URL}/api/consultants/infographics/${pt}`,
    {
      headers: {
        Authorization: `Bearer ${token}`
      },
    }).then(
    (response) => {
      if (!response.ok) {
        throw new Error(`HTTP error ${response.status}`);
      }
      return response.json();
    }
  );
};

export const getAllClientsAndPts = async (includePgps: boolean, token: string) => {
  return await fetch(
    `${BASE_URL}/api/consultants/getAllClientsAndPts?includePgps=${includePgps}`,
    {
      headers: {
        Authorization: `Bearer ${token}`
      },
    }
  ).then((response) => {
    if (!response.ok) {
      throw new Error(`HTTP error ${response.status}`);
    }
    return response.json();
  });
};
