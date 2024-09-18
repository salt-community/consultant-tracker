import { createRoot } from "react-dom/client";
import "./index.css";
import React from "react";


import { router } from "./routing/router.tsx";
import { RouterProvider } from "react-router-dom";
import {ClerkProvider, RedirectToSignUp, SignedOut} from "@clerk/clerk-react";

const PUBLISHABLE_KEY = import.meta.env.VITE_CLERK_PUBLISHABLE_KEY;

if (!PUBLISHABLE_KEY) {
  throw new Error("Missing Publishable Key");
}

createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ClerkProvider publishableKey={PUBLISHABLE_KEY}>
      {/*<RouterProvider router={router} />*/}
      <SignedOut>
        <RedirectToSignUp
          signInFallbackRedirectUrl="/Consultant-Tracker/"
        />
      </SignedOut>
    </ClerkProvider>
  </React.StrictMode>
);
