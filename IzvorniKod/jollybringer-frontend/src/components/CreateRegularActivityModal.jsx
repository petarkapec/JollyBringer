import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import useAuth from '../hooks/useAuth';
import API from './api.js'; // Import the API class

const CreateRegularActivityModal = ({ isOpen, onClose, groupId, onActivityCreated, prefillData }) => {
  const { user } = useAuth();
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    date: '',
  });

  useEffect(() => {
    if (prefillData) {
      setFormData({
        ...formData,
        name: prefillData.activity_name,
        description: prefillData.description,
      });
    }
  }, [prefillData]);

  if (!isOpen) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newActivity = {
      activity_name: formData.name,
      description: formData.description,
      date: new Date(formData.date).toISOString(),
      activity_status: 'InProgress', // Always send status as 'InProgress'
      group_id: groupId,
      created_by: user.username,
    };

    try {
      await API.post(`/groups/${groupId}/activities/regular`, newActivity); // Using API.post()
      toast.success('Activity created successfully');
      setFormData({ name: '', date: '', description: '' });
      onActivityCreated();
      onClose();
    } catch (error) {
      toast.error(error.response?.data?.error || 'Failed to create activity');
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
      <div className="bg-white rounded-lg p-6 max-w-md w-full mx-4">
        <h2 className="text-xl font-semibold text-red-700 mb-4">Create Regular Activity</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-red-700 mb-2">Name</label>
            <input
              type="text"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              className="w-full p-2 rounded bg-red-700 text-white"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-red-700 mb-2">Description</label>
            <textarea
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              className="w-full p-2 rounded bg-red-700 text-white"
              rows="3"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-red-700 mb-2">Date</label>
            <input
              type="date"
              value={formData.date}
              onChange={(e) => setFormData({ ...formData, date: e.target.value })}
              className="w-full p-2 rounded bg-red-700 text-white"
              required
            />
          </div>
          <div className="flex justify-end gap-4">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 text-red-700 hover:text-gray-300"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
            >
              Create Activity
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateRegularActivityModal;