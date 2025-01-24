import React from 'react';
import { toast } from 'react-toastify';
import useAuth from '../hooks/useAuth.js';
import Feedback from './Feedback.jsx';
import API from './api.js'; // Import the API class

const ActivityDetailModal = ({ activity, isOpen, onClose, onActivityDeleted }) => {
  const { role } = useAuth();

  if (!isOpen || !activity) return null;

  const handleDelete = async () => {
    try {
      onActivityDeleted(activity.id);
      toast.success('Activity deleted successfully');
      onClose();
    } catch (error) {
      toast.error('Failed to delete activity');
    }
  };

  const handleMarkAsDone = async () => {
    try {
      await API.put(`/activities/${activity.id}/completed`, { activity_status: 'Completed' });
      toast.success('Activity marked as done');
      onClose();
    } catch (error) {
      console.log(error);
      toast.error('Failed to mark activity as done');
    }
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center z-50 overflow-y-auto">
      <div className="absolute inset-0 bg-black opacity-70" onClick={onClose}></div>
      <div className="bg-white rounded-lg p-6 max-w-md w-full mx-4 z-10">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-semibold text-red-700">{activity.activityName}</h2>
          <button
            onClick={onClose}
            className="text-red-700 hover:text-white"
          >
            ✕
          </button>
        </div>
        <div className="space-y-4">
          <p className="text-red-600">{activity.description}</p>
          <p className="text-sm text-red-600">
            Date: {new Date(activity.date).toLocaleDateString()}
          </p>
          <p className="text-xs text-red-600">
            Status: {activity.activity_status}
          </p>
          {(role === 'President' || role === 'Admin') && (
            <div className="flex gap-4">
              <button
                onClick={handleDelete}
                className="px-4 py-2 bg-red-700 text-white rounded-md hover:bg-red-700"
              >
                Delete Activity
              </button>
              <button
                onClick={handleMarkAsDone}
                className="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
              >
                Mark as Done
              </button>
            </div>
          )}
          <Feedback activityId={activity.id}/>
        </div>
      </div>
    </div>
  );
};

export default ActivityDetailModal;