import "./basic-info.css";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setPersonalData } from "../../../store/slices/BasicInfoSlice";
import { AppDispatch, RootState } from "../../../store/store";
import CardDetails from "../../card-details/card-details";
import TimeItemDetails from "../../time-item-details/time-item-details";
import BasicInfoHeader from "./header/header";
import { getConsultantById } from "../../../api";
import { useAuth } from "@clerk/clerk-react";
import { template } from "../../../constants";

const BasicInfo = () => {
  const dispatch = useDispatch<AppDispatch>();
  const personalData = useSelector(
    (state: RootState) => state.basicInfo.personalData
  );
  const openModal = useSelector(
    (state: RootState) => state.ganttChart.openModal
  );
  const { getToken, signOut } = useAuth();

  const fetchConsultantById = (token: string) => {
    if (id)
      getConsultantById(id, token).then((res) => {
        dispatch(setPersonalData(res));
      });
  };
  const id = useSelector((state: RootState) => state.ganttChart.id);
  useEffect(() => {
    let token: string | null = "";
    const getAccesstoken = async () => {
      token = await getToken({ template });
    };
    getAccesstoken();
    if (!token) {
      signOut();
      return;
    }
    fetchConsultantById(token);
  }, [id]);

  return (
    personalData && (
      <div className={openModal ? "basic-info__wrapper show" : "hide"}>
        <BasicInfoHeader name={personalData.fullName} />
        <div className="basic-info__data">
          <CardDetails />
        </div>
        <TimeItemDetails />
      </div>
    )
  );
};

export default BasicInfo;
