import { createBrowserRouter } from "react-router-dom";
import { PageNotFound } from "../components/page-not-found/page-not-found.jsx";
import Home from "../view/home/home.tsx";

export const router = createBrowserRouter([
  {
    path: "/Consultant-Tracker/",
    element: <Home />,
    errorElement: <PageNotFound />,
  },
]);
