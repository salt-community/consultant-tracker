const HeaderAvatar = () => {
  const image = "/avatar.svg";
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