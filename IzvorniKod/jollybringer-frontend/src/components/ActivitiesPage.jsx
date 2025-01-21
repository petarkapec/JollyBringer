import React, { useState, useEffect } from 'react';
import Header from './Header.jsx';
import Activities from './Activities.jsx';
import ActivitiesPool from './ActivitiesPool.jsx';
import useAuth from '../hooks/useAuth.js';
import { useLocation } from 'react-router-dom'; // Import useLocation

const ActivitiesPage = () => {
  const { role, user } = useAuth();
  const [selectedGroup, setSelectedGroup] = useState(null);
  const location = useLocation(); // Get the current location

  useEffect(() => {
    if (location.state?.selectedGroupId) {
      setSelectedGroup({ id: location.state.selectedGroupId });
    }
  }, [location.state]);

  const handleGroupSelect = (group) => {
    setSelectedGroup(group);
  };

  return (
    <div className="bg-black h-screen flex flex-col">
      <Header onGroupSelect={handleGroupSelect} />
      <div className="flex flex-grow">
        <div className="w-1/2 m-4">
          <Activities selectedGroup={selectedGroup} role={role} />
        </div>
        <div className="w-1/2 m-4">
          <ActivitiesPool groupId={selectedGroup?.id} />
        </div>
      </div>
    </div>
  );
};

export default ActivitiesPage;