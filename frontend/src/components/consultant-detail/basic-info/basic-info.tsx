"use client";
import "./basic-info.css";
import {useEffect} from "react";
import BasicInfoHeader from "@/components/consultant-detail/basic-info/header/header";
import {getConsultantById} from "@/api";
import CardDetails from "@/components/card-details/card-details";
import TimeItemDetails from "@/components/time-item-details/time-item-details";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, RootState} from "@/store/store";
import {setPersonalData} from "@/store/slices/BasicInfoSlice";


const BasicInfo = () => {
  const dispatch = useDispatch<AppDispatch>();
  const personalData = useSelector((state:RootState)=>state.basicInfo.personalData)
  const open = useSelector((state: RootState) => state.ganttChart.open)
  const fetchConsultantById = () => {
    if (id) getConsultantById(id).then((res) => {
      dispatch(setPersonalData(res))
    });
  };
  const id = useSelector((state: RootState) => state.ganttChart.id)
  useEffect(() => {
    fetchConsultantById();
  }, [id]);

  return (
    personalData && (
      <div className={open ? "basic-info__wrapper show" : "hide"}>
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
