import React, { useEffect, useState } from 'react';
import API from './api.js';  // Import the API class
import { toast } from 'react-toastify';

const ActivitiesPool = ({ groupId }) => {
  const [activities, setActivities] = useState([]);

  useEffect(() => {
    const fetchAllActivities = async () => {
      try {
        const data = await API.get(`/activities/${groupId}/pool`);  // Fetch all activities from the new endpoint
        setActivities(data);
      } catch (error) {
        toast.error('Failed to fetch activities');
      }
    };

    if (groupId) {
      fetchAllActivities();
    }
  }, [groupId]);

  return (
    <div className="p-4 bg-gray-800 rounded-lg">
      <h2 className="text-2xl font-bold mb-4 text-white">All Activities</h2>
      <ul className="space-y-2">
        {activities.map((activity) => (
          <li key={activity.id} className="bg-gray-700 p-2 rounded text-white">
            {activity.activity_name}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ActivitiesPool;