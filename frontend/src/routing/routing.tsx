import { createBrowserRouter } from "react-router-dom";
import App from "../App.jsx";
import { PageNotFound } from "../components/page-not-found/page-not-found.jsx";
import Home from "../view/home/home.tsx";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
    errorElement: <PageNotFound />,
  },
]);
