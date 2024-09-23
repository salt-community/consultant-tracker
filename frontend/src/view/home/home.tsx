import Dashboard from "../../components/dashboard/dashboard";
import Navbar from "../../components/navbar/navbar";
import "./home.css";

const Home = () => {

  return (
    <>
    <Navbar />
    <div className="page-wrapper">
      <Dashboard />
    </div>
    </>
  );
};
export default Home;
