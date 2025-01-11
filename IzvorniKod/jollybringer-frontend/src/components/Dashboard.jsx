import { useState, useEffect } from 'react';
import axios from 'axios';
import useAuth from '../hooks/useAuth';
import Header from "./Header.jsx";
import Activities from "./Activities.jsx";

const Dashboard = () => {
  const [isMenuVisible, setIsMenuVisible] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [groups, setGroups] = useState([]);
  const [selectedGroup, setSelectedGroup] = useState(null);
  const { role, user, loading } = useAuth();

  useEffect(() => {
    const fetchGroups = async () => {
      try {
        const response = await axios.get(`${import.meta.env.VITE_BACKEND_URL}/groups`, { withCredentials: true });
        setGroups(response.data);
      } catch (error) {
        console.error('Error fetching groups:', error);
      }
    };

    fetchGroups();
  }, []);

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

  const handleNewGroupClick = () => {
    setIsModalVisible(true);
  };

  const handleLogout = async () => {
    try {
      await axios.post(`${import.meta.env.VITE_BACKEND_URL}/logout`, {}, { withCredentials: true });
      window.location.href = '/';
    } catch (error) {
      console.error('Error logging out:', error);
    }
  };

  const handleAdminRedirect = () => {
    window.location.href = '/dashboard/admin';
  };

  const handleGroupSelect = (group) => {
    setSelectedGroup(group);
  };

  const updateGroups = async () => {
    try {
      const response = await axios.get('http://localhost:8080/groups', { withCredentials: true });
      setGroups(response.data);
    } catch (error) {
      console.error('Error fetching groups:', error);
    }
  };

  const handleGroupCreated = (newGroup) => {
    setGroups((prevGroups) => [...prevGroups, newGroup]);
    setSelectedGroup(newGroup);
  };

  return (
    <div className={'bg-black'}>
      <Header/>
      <div className={'flex justify-between'}>
        <div className={'w-1/2'}>
          <Activities selectedGroup={selectedGroup} role={role}/>
        </div>
        <div className={'w-1/2'}>
          {/*<Chat/>*/}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;