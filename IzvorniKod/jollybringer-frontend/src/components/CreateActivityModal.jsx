import React, { useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import useAuth from '../hooks/useAuth.js';

const CreateActivityModal = ({ isOpen, onClose, groupId, onActivityCreated }) => {
  const { user } = useAuth();
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    date: '',
    status: 'Pending'
  });

  if (!isOpen) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newActivity = {
      GROUP_ID: groupId,
      ACTIVITY_NAME: formData.name,
      CREATED_BY: user.username,
      DATE: formData.date,
      DESCRIPTION: formData.description,
      ACTIVITY_STATUS: formData.status
    };

    console.log(newActivity)

    try {
      await axios.post(`http://localhost:8080/groups/${groupId}/activities`, newActivity, { withCredentials: true });
      toast.success('Activity created successfully');
      setFormData({ name: '', date: '', description: '', status: 'Pending' });
      onActivityCreated();
      onClose();
    } catch (error) {
      toast.error(error.response?.data?.error || 'Failed to create activity');
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
      <div className="bg-customGray rounded-lg p-6 max-w-md w-full mx-4">
        <h2 className="text-xl font-semibold text-white mb-4">Create Activity</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-white mb-2">Name</label>
            <input
              type="text"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              className="w-full p-2 rounded bg-gray-700 text-white"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-white mb-2">Description</label>
            <textarea
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              className="w-full p-2 rounded bg-gray-700 text-white"
              rows="3"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-white mb-2">Date</label>
            <input
              type="date"
              value={formData.date}
              onChange={(e) => setFormData({ ...formData, date: e.target.value })}
              className="w-full p-2 rounded bg-gray-700 text-white"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-white mb-2">Status</label>
            <select
              value={formData.status}
              onChange={(e) => setFormData({ ...formData, status: e.target.value })}
              className="w-full p-2 rounded bg-gray-700 text-white"
              required
            >
              <option value="Pending">Pending</option>
              <option value="Completed">Completed</option>
            </select>
          </div>
          <div className="flex justify-end gap-4">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 text-white hover:text-gray-300"
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

export default CreateActivityModal;