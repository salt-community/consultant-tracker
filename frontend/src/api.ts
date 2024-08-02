export const getDashboardData = async () => {
    return await fetch("http://localhost:8080/api/consultants")
    .then((response) => response.json());
  };