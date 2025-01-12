import React from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import useAuth from '../hooks/useAuth.js';
import Feedback from './Feedback.jsx';

const ActivityDetailModal = ({ activity, isOpen, onClose, onActivityDeleted }) => {
  const { role } = useAuth();

  if (!isOpen || !activity) return null;

  const handleDelete = async () => {
    try {
      onActivityDeleted(activity.id);
      toast.success('Activity deleted successfully');
      onClose();
    } catch (error) {
      console.log(error)
      toast.error('Failed to delete activity');
    }
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center z-50">
      <div className="absolute inset-0 bg-black opacity-70" onClick={onClose}></div>
      <div className="bg-customGray rounded-lg p-6 max-w-md w-full mx-4 z-10">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-semibold text-white">{activity.ACTIVITY_NAME}</h2>
          <button
            onClick={onClose}
            className="text-gray-400 hover:text-white"
          >
            ✕
          </button>
        </div>
        <div className="space-y-4">
          <p className="text-gray-300">{activity.description}</p>
          <p className="text-sm text-gray-400">
            Date: {new Date(activity.date).toLocaleDateString()}
          </p>
          <p className="text-xs text-gray-500">
            Status: {activity.activity_status}
          </p>
          {(role === 'President' || role === 'Admin') && (
            <button
              onClick={handleDelete}
              className="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700"
            >
              Delete Activity
            </button>
          )}
          <Feedback activityId={activity.id} />
        </div>
      </div>
    </div>
  );
};

export default ActivityDetailModal;