import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { InfographicResponseType } from "../../../types";
import { getInfographicsByPt } from "../../../api";
import Infographic from "./infographic/infographic";
import { AppDispatch, RootState } from "../../../store/store";
import { setInfographicData } from "../../../store/slices/DashboardHeaderSlice";

const DashboardHeader = () => {
  const data = useSelector(
    (state: RootState) => state.dashboardHeader.infographicData
  );
  const token = useSelector((state: RootState) => state.token.token);
  const user = useSelector((state: RootState) => state.authorization.user);

  const dispatch = useDispatch<AppDispatch>();
  const userFirstName = user.split(" ")[0];

  useEffect(() => {
    token != "" &&
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
  }, [token]);

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
