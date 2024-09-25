import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { InfographicResponseType } from "../../../types";
import { getInfographicsByPt } from "../../../api";
import Infographic from "./infographic/infographic";
import { AppDispatch, RootState } from "../../../store/store";
import { setInfographicData } from "../../../store/slices/DashboardHeaderSlice";
import { useAuth } from "@clerk/clerk-react";
import { template } from "../../../constants";

const DashboardHeader = () => {
  const data = useSelector(
    (state: RootState) => state.dashboardHeader.infographicData
  );
  const user = useSelector((state: RootState) => state.authorization.user);
  const dispatch = useDispatch<AppDispatch>();
  const userFirstName = user.split(" ")[0];
  const { getToken, signOut } = useAuth();


  useEffect(() => {
    let token: string | null = "";
    const getAccesstoken = async () => {
      token = await getToken({template});
    };
    getAccesstoken();
    if (!token) {
      signOut();
      return;
    }
      getInfographicsByPt(user, token)
        .then((res: InfographicResponseType) => {
          const infographics = [
            {
              title: "Total consultants",
              amount: res.totalConsultants,
              variant: "blue",
            },
            {
              title: "Total PGP",
              amount: res.totalPgpConsultants,
              variant: "green",
            },
            {
              title: `${userFirstName}'s consultants`,
              amount: res.ptsConsultants,
              variant: "violet",
            },
          ];
          const filteredInfographics = infographics.filter(el=> {
            return !(el.variant === "violet" && el.amount === 0);
          })
          dispatch(setInfographicData(filteredInfographics));
        });
  }, []);

  return (
    <div className="dashboard-infographic__card">
      {data.map((element, index) => {
        const { title, amount, variant } = element;
        return (
          <Infographic
            key={index}
            title={title}
            amount={amount}
            variant={variant}
          />
        );
      })}
    </div>
  );
};

export default DashboardHeader;
