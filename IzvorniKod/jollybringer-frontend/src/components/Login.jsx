import '../styles/Login.css';
import CountdownTimer from "./CountdownTimer.jsx";

const Login = () => {

  const handleGoogleLogin = () => {
    window.location.href = `${import.meta.env.VITE_BACKEND_URL}/oauth2/authorization/google`;
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