import App from './page'
import React from "react";

export default function RootLayout({children}: Readonly<{ children: React.ReactNode}>) {
  return (
    <App>{children}</App>
  );
}

