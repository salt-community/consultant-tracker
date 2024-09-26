import './error.css';

type Props = {
  message: string
}

export const Error = ({message}: Props) => {
  return (
    <div className="error-container">
      {message}
    </div>
  );
};