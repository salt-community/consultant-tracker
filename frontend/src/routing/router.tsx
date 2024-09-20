import { createBrowserRouter } from "react-router-dom";
import App from "../App.jsx";
import { PageNotFound } from "../components/page-not-found/page-not-found.jsx";

export const router = createBrowserRouter([
  {
    path: "/consultant-tracker/",
    element: <App />,
    errorElement: <PageNotFound />,
  },
]);
