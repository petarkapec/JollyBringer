'use client';
import {useSession, signIn, signOut} from 'next-auth/react';
import {useEffect, useState} from 'react';
import { useRouter } from 'next/router';
import CountdownTimer from "../components/CountdownTimer";
import Activities from "../components/Activities";
import Chat from "../components/Chat";
import Modal from "../components/Modal";

export default function Dashboard() {
  const { data: session, status } = useSession();
  const router = useRouter();

  useEffect(() => {
    if (status === 'unauthenticated') {
      router.push('/login');
    }
  }, [status]);

  const [isMenuVisible, setIsMenuVisible] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);

  const handleNewGroupClick = () => {
    setIsModalVisible(true);
  };

  const handleLogout = async () => {
    await signOut();
  };


  if (status === 'loading') {
    return <div>Loading...</div>;
  }

  if (status === 'authenticated') {
    return (
      <div className="dashboard-container">
        <header className="dashboard-header">
          <div className='logo'>Jolly Bringer</div>
          <CountdownTimer page={'Dashboard'}/>
          <div className='dashboard-header-user-data'>
            <div
              className="groups"
              onMouseEnter={() => setIsMenuVisible(true)}
              onMouseLeave={() => setIsMenuVisible(false)}
            >
              Groups
              {isMenuVisible && (
                <div className="extended-menu">
                  <ul>
                    <li>Group 1</li>
                    <li>Group 2</li>
                    <li onClick={handleNewGroupClick}>+ New group</li>
                  </ul>
                </div>
              )}
            </div>
            <button className="dashboard-header-button" onClick={handleLogout}>Logout</button>
          </div>
        </header>
        <div className="dashboard-content">
          <Activities/>
          <Chat/>
        </div>
        <Modal isVisible={isModalVisible} onClose={() => setIsModalVisible(false)}/>
      </div>
    );
  }

  return null;
}