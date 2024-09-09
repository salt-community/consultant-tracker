const NotFoundPage = () => {
  return (
    <div className="not-found__container">
      <h1>404</h1>
      <p>Oops! Something went wrong.</p>
      <a className="button" href="/home">
        Go back to Homepage
      </a>
    </div>
  );
};

export default NotFoundPage;