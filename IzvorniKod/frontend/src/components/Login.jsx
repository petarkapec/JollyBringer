import '../styles/Login.css';
import CountdownTimer from "./CountdownTimer.jsx";
import { GoogleLogin } from '@react-oauth/google';

const Login = () => {
  const clientId = import.meta.env.VITE_GOOGLE_CLIENT_ID; // Dinamički unos Client ID
  const redirectUri = import.meta.env.VITE_GOOGLE_REDIRECT_URI; // Dinamički unos Redirect URI

<<<<<<< Updated upstream:IzvorniKod/frontend/src/components/Login.jsx
  const handleGoogleLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/google"
  }
=======
  const handleLogin = () => {
    const googleAuthUrl = `https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=${clientId}&scope=openid%20profile%20email&redirect_uri=${redirectUri}&nonce=nonce_value`;
    window.location.href = googleAuthUrl;
  };

  const handleSuccess = (response) => {
    console.log(response); // Ovo sadrži token od Googlea

    fetch(`${import.meta.env.VITE_BACKEND_URL}/check-auth`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${response.credential}`, // Koristimo token iz Google autentifikacije
        'Content-Type': 'application/json',
      },
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.isAuthenticated) {
          console.log('Server response:', data);
          localStorage.setItem('authToken', response.credential); // Spremi Google token za buduću upotrebu
          localStorage.setItem('userData', JSON.stringify(data)); // Spremi dodatne korisničke podatke
          window.location.href = '/Dashboard'; // Redirekcija na Dashboard
        } else {
          console.error('Unauthorized:', data);
        }
      })
      .catch((error) => {
        console.error('Error during login:', error);
      });
  };

  const handleFailure = () => {
    console.error('Login failed');
  };
>>>>>>> Stashed changes:IzvorniKod/jollybringer-frontend/src/components/Login.jsx

  return (
    <div className='login-wrapper'>
      <h1>Login</h1>
      <div className='login-btn-container'>
        <GoogleLogin
          onSuccess={handleSuccess}
          onError={handleFailure}
        />
        <button onClick={handleLogin} className="custom-google-login">
          Login with Google (Custom Flow)
        </button>
      </div>
      <CountdownTimer page={'Login'} />
    </div>
  );
};

<<<<<<< Updated upstream:IzvorniKod/frontend/src/components/Login.jsx
export default Login
=======
export default Login;
>>>>>>> Stashed changes:IzvorniKod/jollybringer-frontend/src/components/Login.jsx
