"use client"
import {useEffect} from "react";
import {InfographicResponseType} from "@/types";
import Infographic from "@/components/dashboard/dashboard-header/infographic/infographic";
import {getInfographicsByPt} from "@/api";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, RootState} from "@/store/store";
import {setInfographicData} from "@/store/slices/DashboardHeaderSlice";
import {user} from "@/utils/utils";


const DashboardHeader = () => {
  const data = useSelector((state: RootState) => state.dashboardHeader.infographicData)
  const dispatch = useDispatch<AppDispatch>();
  const userFirstName = user.split(" ")[0]; //TODO change when authentication implemented

  useEffect(() => {
    getInfographicsByPt(user) //TODO change when authentication implemented
      .then((res: InfographicResponseType) => {
        const infographics = [
          {
            title: "Total consultants",
            amount: res.totalConsultants,
            variant: "blue"
          },
          {
            title: "Total PGP",
            amount: res.totalPgpConsultants,
            variant: "green"
          },
          {
            title: `${userFirstName}'s consultants`,
            amount: res.ptsConsultants,
            variant: "violet"
          }
        ];
        dispatch(setInfographicData(infographics));
      })
  }, []);

  return (
    <div className="dashboard-infographic__card">
      {data.map((element, index) => {
        const {title, amount, variant} = element;
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