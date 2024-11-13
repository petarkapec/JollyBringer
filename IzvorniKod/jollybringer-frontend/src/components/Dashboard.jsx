import { useState } from 'react';
import axios from 'axios';
import useAuth from '../hooks/useAuth';
import '../styles/Dashboard.css';
import Activities from "./Activities.jsx";
import Chat from "./Chat.jsx";
import CountdownTimer from "./CountdownTimer.jsx";
import Modal from './Modal.jsx';

const Dashboard = () => {
  const [isMenuVisible, setIsMenuVisible] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const { role, groups } = useAuth();
  // const role = 'Admin';
  // const groups = ['Group 1', 'Group 2', 'Group 3'];

  const handleNewGroupClick = () => {
    if (role === 'Participant') {
      setIsModalVisible(true);
    } else {
      // Handle new group creation for President and Admin
    }
  };

  const handleAdminRedirect = () => {
    window.location.href = '/dashboard/admin';
  };

  const handleLogout = async () => {
    try {
      await axios.post('http://localhost:8080/logout', {}, { withCredentials: true });
      window.location.href = '/';
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

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
            {role}
            {isMenuVisible && (
              <div className="extended-menu">
                <ul>
                  {groups.map(group => (
                    <li key={group}>{group}</li>
                  ))}
                  <li onClick={handleNewGroupClick}>+ New group</li>
                  {(role === 'Admin') && (
                    <li onClick={handleAdminRedirect}>Admin Dashboard</li>
                  )}
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
};

export default Dashboard;