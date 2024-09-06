import Dashboard from "@/components/dashboard/dashboard";
import './page.css'
import Navbar from "@/components/navbar/navbar";

const Home = () => {
  return <>
    <Navbar/>
    <div className="page-wrapper">
      <Dashboard/>
    </div>
  </>
};
export default Home;
