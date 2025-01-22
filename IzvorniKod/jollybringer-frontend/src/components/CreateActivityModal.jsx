import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import useAuth from '../hooks/useAuth';
import API from './api.js'; // Import the API class
import ActivitySelectionModal from './ActivitySelectionModal.jsx'; // Import the ActivitySelectionModal component

const CreateActivityModal = ({ isOpen, onClose, groupId, onActivityCreated, prefillData }) => {
  const { user } = useAuth();
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    day: 1,
    status: 'InProgress',
  });
  const [isSelectionModalOpen, setIsSelectionModalOpen] = useState(false);

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

    const currentYear = new Date().getFullYear();
    const date = new Date(currentYear, 11, formData.day); // December of the current year

    const newActivity = {
      activity_name: formData.name,
      description: formData.description,
      date: date.toISOString(),
      activity_status: formData.status,
      group_id: groupId,
      created_by: user.username,
    };

    try {
      await API.post(`/groups/${groupId}/activities`, newActivity); // Using API.post()
      toast.success('Activity created successfully');
      setFormData({ name: '', day: 1, description: '', status: 'InProgress' });
      onActivityCreated();
      onClose();
    } catch (error) {
      toast.error(error.response?.data?.error || 'Failed to create activity');
    }
  };

  const handleSelectActivity = (activity) => {
    setFormData({
      ...formData,
      name: activity.activityName,
      description: activity.description,
    });
    setIsSelectionModalOpen(false);
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-[9999]">
      <div className="bg-white rounded-lg p-6 max-w-md w-full mx-4">
        <h2 className="text-xl font-semibold text-red-700 mb-4">Create Activity</h2>
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
            <button
              type="button"
              onClick={() => setIsSelectionModalOpen(true)}
              className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-blue-700"
            >
              Select from Existing Activities
            </button>
          </div>
          <div className="mb-4">
            <label className="block text-red-700 mb-2">Day</label>
            <select
              value={formData.day}
              onChange={(e) => setFormData({ ...formData, day: e.target.value })}
              className="w-full p-2 rounded bg-red-700 text-white"
              required
            >
              {Array.from({ length: 25 }, (_, i) => i + 1).map((day) => (
                <option key={day} value={day}>
                  {day}
                </option>
              ))}
            </select>
          </div>
          <div className="mb-4">
            <label className="block text-red-700 mb-2">Status</label>
            <select
              value={formData.status}
              onChange={(e) => setFormData({ ...formData, status: e.target.value })}
              className="w-full p-2 rounded bg-red-700 text-white"
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
      {isSelectionModalOpen && (
        <ActivitySelectionModal
          isOpen={isSelectionModalOpen}
          onClose={() => setIsSelectionModalOpen(false)}
          groupId={groupId}
          onSelectActivity={handleSelectActivity}
        />
      )}
    </div>
  );
};

export default CreateActivityModal;
