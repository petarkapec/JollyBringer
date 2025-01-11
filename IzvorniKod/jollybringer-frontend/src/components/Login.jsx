import '../styles/Login.css';
import CountdownTimer from "./CountdownTimer.jsx";

const Login = () => {

  const handleGoogleLogin = () => {
    window.location.href = `${import.meta.env.VITE_BACKEND_URL}/oauth2/authorization/google`;
  }

  return (
    <div className={'login-background flex justify-center h-dvh items-center'}>
      <div className={'login-wrapper border border-white rounded-[8px] p-12 py-[60px] flex flex-col justify-center items-center gap-7 backdrop-blur bg-black bg-opacity-50'}>
        <h1 className={'text-4xl text-white'}>Login</h1>
        <button
          className={'text-white border-green-600 bg-green-800 border rounded-[8px] p-[8px] px-6 hover:bg-green-700 transition duration-200 mb-8'}
          onClick={handleGoogleLogin}>
          Sign in with Google
        </button>
        <CountdownTimer page={'Login'}/>
      </div>
    </div>
  )
}

export default Login;