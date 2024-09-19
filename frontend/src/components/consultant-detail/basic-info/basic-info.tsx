import "./basic-info.css";
import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import { setPersonalData } from "../../../store/slices/BasicInfoSlice";
import { AppDispatch, RootState } from "../../../store/store";
import CardDetails from "../../card-details/card-details";
import TimeItemDetails from "../../time-item-details/time-item-details";
import BasicInfoHeader from "./header/header";
import { getConsultantById } from "../../../api";



const BasicInfo = () => {
  const dispatch = useDispatch<AppDispatch>();
  const personalData = useSelector((state:RootState)=>state.basicInfo.personalData)
  const openModal = useSelector((state: RootState) => state.ganttChart.openModal)
  const token = useSelector((state: RootState) => state.token.token)
  
  const fetchConsultantById = () => {
    if (id) getConsultantById(id, token).then((res) => {
      dispatch(setPersonalData(res))
    });
  };
  const id = useSelector((state: RootState) => state.ganttChart.id)
  useEffect(() => {
    fetchConsultantById();
  }, [id]);

  return (
    personalData && (
      <div className={openModal ? "basic-info__wrapper show" : "hide"}>
        <BasicInfoHeader name={personalData.fullName}/>
        <div className="basic-info__data">
          <CardDetails/>
        </div>
        <TimeItemDetails/>
      </div>
    )
  );
};

export default BasicInfo;
