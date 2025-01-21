import React, { useEffect, useState } from 'react';
import API from './api.js';  // Import the API class
import { toast } from 'react-toastify';
import CreateActivityModal from './CreateActivityModal.jsx';
import AddToPoolModal from './AddToPoolModal.jsx';

const ActivitiesPool = ({ groupId, onActivityCreated }) => {
  const [activities, setActivities] = useState([]);
  const [selectedActivity, setSelectedActivity] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isAddToPoolModalOpen, setIsAddToPoolModalOpen] = useState(false);

  const fetchAllActivities = async () => {
    try {
      const data = await API.get(`/activities/${groupId}/pool`);
      setActivities(data);
    } catch (error) {
      toast.error('Failed to fetch activities');
    }
  };

  useEffect(() => {
    if (groupId) {
      fetchAllActivities();
    } else {
      // Hardcoded activities for testing purposes
      setActivities([
        { id: 1, activity_name: 'Decorate the Christmas Tree', description: 'Decorate the Christmas tree with lights and ornaments.' },
        { id: 2, activity_name: 'Bake Christmas Cookies', description: 'Bake delicious Christmas cookies.' },
        { id: 3, activity_name: 'Write Letters to Santa', description: 'Write letters to Santa Claus.' },
        { id: 4, activity_name: 'Watch a Christmas Movie', description: 'Watch a classic Christmas movie.' },
        { id: 5, activity_name: 'Make a Gingerbread House', description: 'Build and decorate a gingerbread house.' },
      ]);
    }
  }, [groupId]);

  const handleAddToCalendar = (activity) => {
    setSelectedActivity(activity);
    setIsModalOpen(true);
  };

  const handleAddToPool = () => {
    setIsAddToPoolModalOpen(true);
  };

  return (
    <div className="p-4 bg-gray-800 rounded-lg">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-2xl font-bold text-white">All Activities</h2>
        <button
          onClick={handleAddToPool}
          className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700"
        >
          Add Activity to Pool
        </button>
      </div>
      <ul className="space-y-2">
        {activities.map((activity) => (
          <li key={activity.id} className="bg-gray-700 p-2 rounded text-white flex justify-between items-center">
            <div>
              <h3 className="font-semibold">{activity.activity_name}</h3>
              <p>{activity.description}</p>
            </div>
            <button
              onClick={() => handleAddToCalendar(activity)}
              className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700"
            >
              Add to Calendar
            </button>
          </li>
        ))}
      </ul>
      {isModalOpen && (
        <CreateActivityModal
          isOpen={isModalOpen}
          onClose={() => setIsModalOpen(false)}
          groupId={groupId}
          onActivityCreated={() => {
            setIsModalOpen(false);
            fetchAllActivities();
            onActivityCreated();
          }}
          prefillData={selectedActivity}
        />
      )}
      {isAddToPoolModalOpen && (
        <AddToPoolModal
          isOpen={isAddToPoolModalOpen}
          onClose={() => setIsAddToPoolModalOpen(false)}
          onActivityAdded={fetchAllActivities} 
          groupId={groupId} 
        />
      )}
    </div>
  );
};

export default ActivitiesPool;
