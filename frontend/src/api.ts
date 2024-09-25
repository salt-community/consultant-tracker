const BASE_URL = import.meta.env.VITE_BACKEND_URL;

export const getAuthorization =async(token: string)=>{
  return await fetch(`${BASE_URL}/api/auth`,{
    headers: {
      Authorization: `Bearer ${token}`
    }}).then(response=> response.json())
}

export const getConsultantsData = async (
 searchParams: URLSearchParams,
  token: string,
) => {
  return await fetch(
    `${BASE_URL}/api/consultants?` + searchParams,
    {
      headers: {
        Authorization: `Bearer ${token}`
      },
    }
  )
    .then((response) => response.json())
};

export const getRedDays = async (token: string) => {
  return await fetch(`${BASE_URL}/api/red-days`,
    {
      headers: {
        Authorization: `Bearer ${token}`
      },
    }
  ).then((response) => response.json());
};

export const getConsultantById = async (id: string, token: string) => {
  return await fetch(`${BASE_URL}/api/consultants/${id}`,
    {
      headers: {
        Authorization: `Bearer ${token}`
      },
    }).then((response) => response.json());
};

export const getInfographicsByPt = async (pt: string, token: string) => {
  return await fetch(`${BASE_URL}/api/consultants/infographics/${pt}`,
    {
      headers: {
        Authorization: `Bearer ${token}`
      },
    }).then((response) => response.json());
};

export const getAllClientsAndPts = async (includePgps: boolean, token: string) => {
  return await fetch(
    `${BASE_URL}/api/consultants/all-clients-and-pts?includePgps=${includePgps}`,
    {
      headers: {
        Authorization: `Bearer ${token}`
      },
    }
  ).then((response) => response.json());
};