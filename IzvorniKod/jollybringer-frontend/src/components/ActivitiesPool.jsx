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
    <div className="p-4 bg-customGray rounded-lg">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-2xl font-bold text-white">All Activities</h2>
        <div className="flex gap-2 items-center">
          <button
            onClick={handleCreateWithAI}
            className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700"
          >
            Create with AI
          </button>
          <button
            onClick={() => setIsModalOpen(true)}
            className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700"
          >
            Create Activity
          </button>
        </div>
      </div>
      <ul className="space-y-2">
        {activities.map((activity) => (
          <li key={activity.id} className="bg-customGrayLighter p-2 rounded text-white flex justify-between items-center cursor-pointer" onClick={() => handleActivityClick(activity)}>
            <div>
              <h3 className="font-semibold">{activity.activity_name}</h3>
              <p>{activity.description}</p>
              <p className="text-sm text-gray-400">Date: {new Date(activity.date).toLocaleDateString()}</p>
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
