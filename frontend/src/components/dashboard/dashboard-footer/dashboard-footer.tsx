import './dashboard-footer.css';

const DashboardFooter: React.FC = () => {
  return (
    <footer className="dashboard-footer">
      <p>&copy; {new Date().getFullYear()} School of Applied Technology. All rights reserved.</p>
      <p>&lt;/salt&gt;</p>
    </footer>
  );
};

export default DashboardFooter;
