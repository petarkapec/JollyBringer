import { SessionProvider } from 'next-auth/react';
import '../styles/Login.css'
import '../styles/App.css'
import '../styles/CountdownTimer.css'
import '../styles/Activities.css'
import '../styles/Chat.css'
import '../styles/Modal.css';
import '../styles/Dashboard.css';


export default function App({ Component, pageProps }) {
  return (
    <SessionProvider session={pageProps.session}>
      <Component {...pageProps} />
    </SessionProvider>
  );
}