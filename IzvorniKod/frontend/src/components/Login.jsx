import '../styles/Login.css'

const Login = () => {


  const handleGoogleLogin = () => {
    window.location.href = "/auth/google"
  }

  const handleAaiLogin = () => {
    window.location.href = "/auth/aai"
  }

  return (
    <div className='wrapper'>
      <h1>Login</h1>
      <div className={'btn-container'}>
        <button className={'google-login'} onClick={handleGoogleLogin}>
          Sign in with Google
        </button>


        <div className={'aai-login'} onClick={handleAaiLogin}>
          <button>
            Sign in with AAI@EduHr
          </button>
        </div>
      </div>
    </div>
  )
}

export default Login