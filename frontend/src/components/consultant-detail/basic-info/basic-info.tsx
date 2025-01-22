import "./basic-info.css";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setPersonalData } from "../../../store/slices/BasicInfoSlice";
import { AppDispatch, RootState } from "../../../store/store";
import { CardDetails, TimeItemDetails } from "../../../components";
import { getConsultantById, getConsultantGithubImage } from "../../../api";
import { useAuth } from "@clerk/clerk-react";
import { template } from "../../../constants";
import BasicInfoHeader from "./header/header.tsx";
import { setOpenModal } from "../../../store/slices/GanttChartSlice.ts";

export const BasicInfo = () => {
  const dispatch = useDispatch<AppDispatch>();
  const personalData = useSelector(
    (state: RootState) => state.basicInfo.personalData
  );
  const [githubImageUrl, setGithubImageUrl] = useState<string>("");

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

  const getAccessToken = async () => {
    let token: string | null = "";
    token = await getToken({ template });
    if (!token) {
      await signOut();
      return;
    }
    fetchConsultantById(token);
    fetchConsultantGithubImage(token);
  };

  const fetchConsultantGithubImage = (token: string) => {
    if (id)
      getConsultantGithubImage(id, token)
        .then((res) => {
          setGithubImageUrl(res.githubImageUrl);
        })
        .catch((error) => {
          console.error(`Error fetching githubImageUrl: ${error}`);
        });
  };

  const id = useSelector((state: RootState) => state.ganttChart.id);
  useEffect(() => {
    void getAccessToken();
  }, [id]);

  return (
    personalData && (
      <div className={openModal ? "basic-info__wrapper show" : "hide"}>
        <button
          className="close_button"
          onClick={() => dispatch(setOpenModal(false))}
        >
          &times;
        </button>
        <BasicInfoHeader
          name={personalData.fullName}
          githubImageUrl={githubImageUrl}
        />
        <div className="basic-info__data">
          <CardDetails />
        </div>
        <TimeItemDetails />
      </div>
    )
  );
};
