import '../styles/Login.css'
import {useState} from "react";
import {useNavigate} from "react-router-dom";

const Login = () => {

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate(); // Koristimo useNavigate za preusmeravanje

  const handleGoogleLogin = () => {
    window.location.href = "/auth/google"
  }

  const handleAaiLogin = () => {
    window.location.href = "/auth/aai"
  }


  const handleLogin = async (e) => {
    e.preventDefault();
    const response = await fetch('http://localhost:8080/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({username, password}),
    });

    if (response.ok) {
      const data = await response.json()
      console.log(data)
      navigate('/dashboard')
    } else {
      alert('Invalid credentials')
    }
  };

  return (
    <div className='login-wrapper'>
      <h1>Login</h1>
      <div className={'login-btn-container'}>
        <button className={'google-login'} onClick={handleGoogleLogin}>
          Sign in with Google
        </button>


        <div className={'aai-login'} onClick={handleAaiLogin}>
          <button>
            Sign in with AAI@EduHr
          </button>
        </div>
      </div>
      <div className='login-test-wrapper'>
        <form onSubmit={handleLogin} className='login-form'>
          <input
            type='text'
            placeholder='Username'
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className='login-input'
            required
          />
          <input
            type='password'
            placeholder='Password'
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className='login-input'
            required
          />
          <button type='submit' className='login-submit'>Login</button>
        </form>
      </div>
    </div>
  )
}

export default Login