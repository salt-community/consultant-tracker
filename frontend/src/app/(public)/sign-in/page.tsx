"use client";
import { SignIn } from "@clerk/nextjs";

function Page() {
  return (
    <>
      <div>Wlecome to CT Scan</div>
      <SignIn routing="hash" />
    </>
  );
}

export default Page;
