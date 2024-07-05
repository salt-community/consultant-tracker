import './basic-info.css'
const BasicInfo = () => {
  const image = "/avatar.svg"

  return (
    <aside className="basic-info-section">
      <div className="avatar" style={{ backgroundImage: `url(${image})`, backgroundSize: 'cover' }} />
    </aside>
  );
};

export default BasicInfo;