import '../styles/Login.css';
import CountdownTimer from "./CountdownTimer.jsx";

const backendUrl = import.meta.env.VITE_REACT_APP_BACKEND_URL || 'http://localhost:8080';

const Login = () => {

  const handleGoogleLogin = () => {
    window.location.href = `${backendUrl}/oauth2/authorization/google`;
  }

  return (
    <div className='login-wrapper'>
      <h1>Login</h1>
      <div className={'login-btn-container'}>
        <button className={'google-login'} onClick={handleGoogleLogin}>
          Sign in with Google
        </button>
      </div>
      <CountdownTimer page={'Login'}/>
    </div>
  )
}

export default Login;
