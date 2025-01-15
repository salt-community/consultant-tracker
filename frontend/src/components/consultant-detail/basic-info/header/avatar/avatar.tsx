type Props = {
  gitHubImgUrl:string;
};

const HeaderAvatar = ({gitHubImgUrl}:Props) => {
  console.log("github url: " + gitHubImgUrl);
  let image = "/avatar.svg";
  if(gitHubImgUrl){
    image = gitHubImgUrl;
  }

  return (
    <aside className="basic-info-section">
      <div
        className="avatar"
        style={{backgroundImage: `url(${image})`}}
      />
    </aside>
  );
};

export default HeaderAvatar;