import { useState, useEffect } from 'react';
import axios from 'axios';
import useAuth from '../hooks/useAuth';

const Dashboard = () => {
  const [isMenuVisible, setIsMenuVisible] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [groups, setGroups] = useState([]);
  const [selectedGroup, setSelectedGroup] = useState(null);
  const { role, user, loading } = useAuth();

  useEffect(() => {
    const fetchGroups = async () => {
      try {
        const response = await axios.get('http://localhost:8080/groups', { withCredentials: true });
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
      await axios.post('http://localhost:8080/logout', {}, { withCredentials: true });
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
      {/*<Header/>*/}
      qeqwqwe
      <div className={'flex justify-between'}>
        <div className={'w-1/2'}>
          {/*<Activities/>*/}
        </div>
        <div className={'w-1/2'}>
          {/*<Chat/>*/}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;