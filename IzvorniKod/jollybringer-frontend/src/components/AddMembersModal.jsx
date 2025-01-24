import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import API from './api.js'; // Import the API class

const AddMembersModal = ({ isOpen, onClose, groupId }) => {
  const [allUsers, setAllUsers] = useState([]);
  const [selectedUsers, setSelectedUsers] = useState([]);
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    const fetchUsersAndGroups = async () => {
      try {
        const users = await API.get('/participants/only');
        const groups = await API.get('/groups');
        const usersInGroups = groups.flatMap(group => group.members.map(user => user.id));
        const usersNotInGroups = users.filter(user => !usersInGroups.includes(user.id));
        setAllUsers(usersNotInGroups);
        setGroups(groups);
      } catch (error) {
        toast.error('Failed to fetch users or groups');
      }
    };

    if (isOpen) {
      fetchUsersAndGroups();
    }
  }, [isOpen]);

  const handleUserSelection = (userId) => {
    setSelectedUsers(prevSelectedUsers =>
      prevSelectedUsers.includes(userId)
        ? prevSelectedUsers.filter(id => id !== userId)
        : [...prevSelectedUsers, userId]
    );
  };

  const handleAddMembers = async () => {
    try {
      const group = groups.find(g => g.name === groupId);
      if (!group) {
        toast.error('Group not found');
        return;
      }

      await API.post(`/groups/${group.id}/add-members`, { users: selectedUsers });
      toast.success('Members added to group successfully');
      onClose();
    } catch (error) {
      toast.error('Failed to add members to group');
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-customGray rounded-lg p-6 max-w-md w-full mx-4">
        <h2 className="text-xl font-semibold text-white mb-4">Add Members to Group</h2>
        <ul className="max-h-40 overflow-y-auto">
          {allUsers.map(user => (
            <li key={user.id} className="flex items-center mb-2">
              <input
                type="checkbox"
                checked={selectedUsers.includes(user.id)}
                onChange={() => handleUserSelection(user.id)}
                className="mr-2"
              />
              <span className="text-white">{user.username}</span>
            </li>
          ))}
        </ul>
        <div className="flex justify-end gap-4 mt-4">
          <button
            type="button"
            onClick={onClose}
            className="px-4 py-2 text-white hover:text-gray-300"
          >
            Cancel
          </button>
          <button
            type="button"
            onClick={handleAddMembers}
            className="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
          >
            Add Members
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddMembersModal;