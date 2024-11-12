import {useSession, signIn} from 'next-auth/react'
import {useEffect} from 'react'
import {useRouter} from 'next/router'
import CountdownTimer from "../components/CountdownTimer";

const Login = () => {
  const {data: session, status} = useSession()
  const router = useRouter()

  useEffect(() => {
    if (status === 'authenticated') {
      router.push('/dashboard')
    }
  }, [status]);

  if (status === 'loading') {
    return <div>Loading...</div>
  }

  return (
    <div className='login-wrapper'>
      <h1>Login</h1>
      <div className={'login-btn-container'}>
        <button onClick={() => signIn('google')}>Login with Google</button>
        <div className={'github-login'}>
          <button onClick={() => signIn('github')}>Login with GitHub</button>
        </div>
      </div>
      <CountdownTimer page={'Login'}/>
    </div>
  )
}

export default Login