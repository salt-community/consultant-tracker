import './error.css';

type Props = {
  message: string
}

const Error = ({message}: Props) => {
  return (
    <div className="error-container">
      {message}
    </div>
  );
};

export default Error;