import React, { useState, useEffect } from 'react';
import Header from './Header.jsx';
import Activities from './Activities.jsx';
import ActivitiesPool from './ActivitiesPool.jsx';
import useAuth from '../hooks/useAuth.js';
import { useLocation } from 'react-router-dom';

const ActivitiesPage = () => {
  const { role, user } = useAuth();
  const [selectedGroup, setSelectedGroup] = useState(null);
  const location = useLocation();
  const [activitiesKey, setActivitiesKey] = useState(0); // Key to force re-render

  useEffect(() => {
    if (location.state?.selectedGroupId) {
      setSelectedGroup({ id: location.state.selectedGroupId });
    }
  }, [location.state]);

  const handleGroupSelect = (group) => {
    setSelectedGroup(group);
  };

  const handleActivityCreated = () => {
    setActivitiesKey(prevKey => prevKey + 1);
  };

  return (
    <div className="h-screen flex flex-col">
      <Header onGroupSelect={handleGroupSelect} />
      <div className="flex flex-grow flex-row md:flex-row bg-cover bg-fixed bg-center" style={{ backgroundImage: "url('/assets/img/christmas.webp')" }}>
        <div className="w-full md:w-1/2 p-4">
          <Activities key={activitiesKey} selectedGroup={selectedGroup} role={role} />
        </div>
        <div className="w-full md:w-1/2 p-4">
          <ActivitiesPool groupId={selectedGroup?.id} onActivityCreated={handleActivityCreated} />
        </div>
      </div>
    </div>
  );
};

export default ActivitiesPage;