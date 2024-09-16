import "./left-side-image.css"

function LeftSideImage() {
  const image = "/avatar.svg";
  return (
    <aside className="basic-info-section">
      <div className="avatar" style={{ backgroundImage: `url(${image})` }} />
    </aside>
  );
}

export default LeftSideImage;
