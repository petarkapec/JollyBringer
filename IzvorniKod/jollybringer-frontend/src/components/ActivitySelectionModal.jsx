import React, { useEffect, useState } from 'react';
import API from './api.js'; // Import the API class
import { toast } from 'react-toastify';

const ActivitySelectionModal = ({ isOpen, onClose, groupId, onSelectActivity }) => {
  const [activities, setActivities] = useState([]);

  useEffect(() => {
    const fetchActivities = async () => {
      try {
        const data = await API.get(`/groups/${groupId}/activities/all`);
        setActivities(data);
      } catch (error) {
        toast.error('Failed to fetch activities');
      }
    };

    if (isOpen) {
      fetchActivities();
    }
  }, [isOpen, groupId]);

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-[9999] overflow-y-auto">
      <div className="bg-white rounded-lg p-6 max-w-md w-full mx-4">
        <h2 className="text-xl font-semibold text-white mb-4">Select Activity</h2>
        <ul className="space-y-2">
          {activities.map((activity) => (
            <li
              key={activity.id}
              className="bg-red-700 p-2 rounded text-white cursor-pointer"
              onClick={() => onSelectActivity(activity)}
            >
              <h3 className="font-semibold">{activity.activityName}</h3>
              <p>{activity.description}</p>
            </li>
          ))}
        </ul>
        <div className="flex justify-end mt-4">
          <button
            onClick={onClose}
            className="px-4 py-2 text-red-700 hover:text-gray-300"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
};

export default ActivitySelectionModal;