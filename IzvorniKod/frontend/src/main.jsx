import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './components/App.jsx'
import { GoogleOAuthProvider } from '@react-oauth/google';

const GOOGLEAPI = import.meta.env.VITE_GOOGLE_CLIENT_ID

createRoot(document.getElementById('root')).render(
  <GoogleOAuthProvider clientId={GOOGLEAPI}>
  <StrictMode>
    <App />
  </StrictMode>
  </GoogleOAuthProvider>,
)
