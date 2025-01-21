type Props = {
  githubImageUrl: string;
};

const HeaderAvatar = ({ githubImageUrl }: Props) => {
  let image = "/avatar.svg";
  if (githubImageUrl) {
    image = githubImageUrl;
  }

  return (
    <aside className="basic-info-section">
      <div className="avatar" style={{ backgroundImage: `url(${image})` }} />
    </aside>
  );
};

export default HeaderAvatar;
