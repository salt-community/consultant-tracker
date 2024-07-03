import './page-wrapper.css'
import {ReactNode} from "react";

type Props = {
  children: ReactNode
}
const PageWrapper = ({children}: Props) => {
  return (
    <main className="page-wrapper">
      {children}
    </main>
  );
};

export default PageWrapper;