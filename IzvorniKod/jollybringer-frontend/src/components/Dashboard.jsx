import {useState} from 'react';
import axios from 'axios';
import '../styles/Dashboard.css';
import Activities from "./Activities.jsx";
import Chat from "./Chat.jsx";
import CountdownTimer from "./CountdownTimer.jsx";
import Modal from './Modal.jsx';

const Dashboard = () => {
  const [isMenuVisible, setIsMenuVisible] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);

  const handleNewGroupClick = () => {
    setIsModalVisible(true);
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
};

export default Dashboard;