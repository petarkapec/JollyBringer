import { useState, useEffect } from 'react';
import Header from './Header.jsx';
import Activities from './Activities.jsx';
import Chat from './Chat.jsx';
import API from './api';  // Import the API class

const Dashboard = () => {
  const [isMenuVisible, setIsMenuVisible] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [groups, setGroups] = useState([]);
  const [selectedGroup, setSelectedGroup] = useState(null);
  const [role, setRole] = useState(null);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Save token from URL to localStorage
  useEffect(() => {
    const saveTokenFromUrl = () => {
      const urlParams = new URLSearchParams(window.location.search);
      const token = urlParams.get("token");

      if (token) {
        localStorage.setItem("authToken", token);
        const cleanUrl = window.location.origin + window.location.pathname;
        window.history.replaceState({}, document.title, cleanUrl);
      } else {
        console.log("No token found in URL.");
      }
    };

    saveTokenFromUrl();
  }, []);

  // Fetch user data using the stored token
  useEffect(() => {
    const fetchUserData = async () => {
      const token = localStorage.getItem('authToken');
      if (!token) {
        window.location.href = '/login';
        return;
      }

      try {
        const data = await API.get('/check-auth');  // Use API class method
        setRole(data.role);
        setUser({
          username: data.username,
          email: data.email,
          id: data.user_id,
        });
      } catch (error) {
        console.error('Error fetching user data:', error);
        window.location.href = '/login';
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, []);

  // Fetch groups from the backend
  useEffect(() => {
    const fetchGroups = async () => {
      try {
        const data = await API.get('/groups');  // Use API class method
        setGroups(data);
      } catch (error) {
        console.error('Error fetching groups:', error);
      }
    };

    fetchGroups();
  }, []);

  // Group selection logic and localStorage sync
  useEffect(() => {
    const savedGroup = JSON.parse(localStorage.getItem('selectedGroup'));
    if (savedGroup) {
      setSelectedGroup(savedGroup);
    }

    const handleStorageChange = () => {
      const updatedGroup = JSON.parse(localStorage.getItem('selectedGroup'));
      if (updatedGroup) {
        setSelectedGroup(updatedGroup);
      }
    };

    window.addEventListener('storage', handleStorageChange);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, []);

  useEffect(() => {
    if (selectedGroup) {
      localStorage.setItem('selectedGroup', JSON.stringify(selectedGroup));
    }
  }, [selectedGroup]);

  if (loading) {
    return <div>Loading...</div>;
  }

  const handleGroupSelect = (group) => {
    setSelectedGroup(group);
  };

  const handleNewGroupClick = () => {
    setIsModalVisible(true);
  };

  const handleLogout = async () => {
    try {
      await API.post('/logout', {});  // Use API class method
      window.location.href = '/';
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

  const handleAdminRedirect = () => {
    window.location.href = '/dashboard/admin';
  };

  const handleGroupCreated = (newGroup) => {
    setGroups((prevGroups) => [...prevGroups, newGroup]);
    setSelectedGroup(newGroup);
  };

  return (
    <div className="dashboard-container">
      <Header onGroupSelect={handleGroupSelect} />
      <div className="flex flex-grow flex-row md:flex-row">
        <div className="w-full md:w-1/2 p-4">
          <Activities selectedGroup={selectedGroup} role={role} />
        </div>
        <div className="w-full md:w-1/2 p-4">
          <Chat user={user} selectedGroup={selectedGroup} />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
