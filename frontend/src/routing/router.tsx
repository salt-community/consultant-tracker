import { createBrowserRouter } from "react-router-dom";
import App from "../app.tsx";
import { PageNotFound } from "../view/page-not-found/page-not-found";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <PageNotFound />,
  },
]);
