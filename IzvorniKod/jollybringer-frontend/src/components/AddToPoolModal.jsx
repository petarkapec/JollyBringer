import React, { useState } from 'react';
import { toast } from 'react-toastify';
import API from './api.js'; // Import the API class

const AddToPoolModal = ({ isOpen, onClose, onActivityAdded, groupId }) => {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
  });

  if (!isOpen) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newActivity = {
      activity_name: formData.name,
      description: formData.description,
      group_id: groupId, 
    };

    try {
      await API.post(`/activities/${groupId}/pool`, newActivity);
      toast.success('Activity added to pool successfully');
      setFormData({ name: '', description: '' });
      onActivityAdded();
      onClose();
    } catch (error) {
      toast.error(error.response?.data?.error || 'Failed to add activity to pool');
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
      <div className="bg-customGray rounded-lg p-6 max-w-md w-full mx-4">
        <h2 className="text-xl font-semibold text-white mb-4">Add Activity to Pool</h2>
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
              Add Activity
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddToPoolModal;