"use client"
import { SignIn, useUser } from "@clerk/nextjs";
import React from "react";
import Home from "./home/page";
import Page from "./(public)/sign-in/page";

export default function App() {
  const { user } = useUser();
  return <>{!user ? <Page /> : <Home />}</>;
}
