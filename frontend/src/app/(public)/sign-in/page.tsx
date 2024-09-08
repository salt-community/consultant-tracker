"use client"
import {ClerkProvider, SignedIn, SignedOut, SignInButton} from "@clerk/nextjs";
import Home from "@/app/(private)/home/page";
import Navbar from "@/components/navbar/navbar";

const Page = () => {
  const PUBLISHABLE_KEY = process.env.NEXT_PUBLIC_CLERK_PUBLISHABLE_KEY;
  if (!PUBLISHABLE_KEY) {
    throw new Error("Missing Publishable Key");
  }
  return (
    <ClerkProvider publishableKey={PUBLISHABLE_KEY}>
      <SignedOut>
        <SignInButton/>
      </SignedOut>
      <SignedIn>
        <Navbar/>
        <Home/>
      </SignedIn>
    </ClerkProvider>
  );
};

export default Page;