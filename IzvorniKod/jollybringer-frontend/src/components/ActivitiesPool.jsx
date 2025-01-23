import React, { useEffect, useState } from 'react';
import API from './api.js';  // Import the API class
import { toast } from 'react-toastify';
import CreateRegularActivityModal from './CreateRegularActivityModal.jsx'; // Import the CreateRegularActivityModal component
import ActivityDetailModal from './ActivityDetailModal.jsx'; // Import the ActivityDetailModal component
import useAuth from '../hooks/useAuth.js'; // Import useAuth hook

const ActivitiesPool = ({ groupId, onActivityCreated }) => {
  const { role } = useAuth(); // Get the user's role
  const [activities, setActivities] = useState([]);
  const [selectedActivity, setSelectedActivity] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isDetailModalOpen, setIsDetailModalOpen] = useState(false);

  const fetchAllActivities = async () => {
    try {
      const data = await API.get(`/groups/${groupId}/activities/regular`);
      setActivities(data);
    } catch (error) {
      toast.error('Failed to fetch activities');
    }
  };

  useEffect(() => {
    if (groupId) {
      fetchAllActivities();
    }
  }, [groupId]);

  const handleCreateWithAI = async () => {
    try {
      const createdActivity = await API.get(`/ai/create-activity/${groupId}/regular`);
      const createdDate = new Date(createdActivity.date).getDate();
      toast.success(`Activity created with AI successfully on day ${createdDate}`);
      fetchAllActivities(); // Refresh activities after creating with AI
    } catch (error) {
      toast.error('Failed to create activity with AI');
    }
  };

  const handleActivityClick = (activity) => {
    setSelectedActivity(activity);
    setIsDetailModalOpen(true);
  };

  const handleActivityDeleted = async (activityId) => {
    try {
      await API.delete(`/activities/${activityId}`);  // Using API class
      setActivities((prevActivities) => prevActivities.filter(activity => activity.id !== activityId));
    } catch (error) {
    }
  };

  return (
    <div className="p-4 rounded-lg bg-white">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-2xl font-bold text-red-700">All Activities</h2>
        <div className="flex gap-2 items-center">
              <button
                onClick={handleCreateWithAI} 
                className="flex flex-col items-center cursor-pointer">
                <img src="\assets\img\elf.png" alt="Elf Icon" className="w-16 h-16" />
                <span className='text-red-700'>Create with AI</span>
              </button>
              <button
                onClick={() => setIsModalOpen(true)}
                className="flex flex-col items-center cursor-pointer"
              >
                <img src='/assets/img/gingerbread.png' className='w-16 h-16 md:w-16 md:h-16'></img>
                <span className='text-red-700'>Create Activity</span>
              </button>
        </div>
      </div>
      <ul className="space-y-2">
        {activities.map((activity) => (
          <li key={activity.id} className="bg-red-700 p-2 rounded text-white flex justify-between items-center cursor-pointer" onClick={() => handleActivityClick(activity)}>
            <div>
              <h3 className="font-semibold">{activity.activity_name}</h3>
              <p>{activity.description}</p>
              <p className="text-sm text-white text-opacity-70">Date: {new Date(activity.date).toLocaleDateString()}</p>
            </div>
          </li>
        ))}
      </ul>
      {isModalOpen && (
        <CreateRegularActivityModal
          isOpen={isModalOpen}
          onClose={() => setIsModalOpen(false)}
          groupId={groupId}
          onActivityCreated={() => {
            setIsModalOpen(false);
            fetchAllActivities();
            onActivityCreated();
          }}
        />
      )}
      {isDetailModalOpen && (
        <ActivityDetailModal
          activity={selectedActivity}
          isOpen={isDetailModalOpen}
          onClose={() => setIsDetailModalOpen(false)}
          onActivityDeleted={handleActivityDeleted}
        />
      )}
    </div>
  );
};

export default ActivitiesPool;
