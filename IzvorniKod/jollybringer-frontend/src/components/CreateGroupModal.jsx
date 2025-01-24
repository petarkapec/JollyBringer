import { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import useAuth from '../hooks/useAuth.js';
import API from './api.js'; // Import the API class

export default function CreateGroupModal({ isOpen, onClose, onGroupCreated }) {
  const [name, setName] = useState('');
  const [selectedUsers, setSelectedUsers] = useState([]);
  const [allUsers, setAllUsers] = useState([]);
  const [groups, setGroups] = useState([]); // State to store groups
  const { role } = useAuth();

  useEffect(() => {
    if (role !== 'Participant') {
      const fetchUsersAndGroups = async () => {
        try {
          const usersResponse = await API.get('/participants/only'); // Using API.get() instead of axios.get()
          const groupsResponse = await API.get('/groups'); // Using API.get() for fetching groups

          const users = usersResponse;
          const groups = groupsResponse;

          const usersInGroups = groups.flatMap(group => group.members.map(user => user.id));
          const usersNotInGroups = users.filter(user => !usersInGroups.includes(user.id));

          setAllUsers(usersNotInGroups);
          setGroups(groups); // Set the groups state
        } catch (error) {
          console.error('Error fetching users or groups:', error);
          toast.error('Failed to fetch users or groups');
        }
      };
      fetchUsersAndGroups();
    }
  }, [role]);

  const handleCreateGroup = async (e) => {
    e.preventDefault();

    // Check if a group with the same name already exists
    const groupExists = groups.some(group => group.name.toLowerCase() === name.toLowerCase());
    if (groupExists) {
      toast.error('A group with this name already exists');
      return;
    }

    try {
      const response = await API.post('/groups', {
        name: name,
        users: selectedUsers
      }); // Using API.post() instead of axios.post()
      const newGroup = response;
      onClose();
      onGroupCreated(newGroup); // Set the newly created group as the selected group
      toast.success('Group created successfully'); // Add toast notification
    } catch (error) {
      console.error('Error creating group:', error);
      toast.error('Failed to create group');
    }
  };

  const handleUserSelection = (userId) => {
    setSelectedUsers(prevSelectedUsers =>
      prevSelectedUsers.includes(userId)
        ? prevSelectedUsers.filter(id => id !== userId)
        : [...prevSelectedUsers, userId]
    );
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
      <div className="bg-customGray rounded-lg p-6 max-w-md w-full mx-4">
        <h2 className="text-xl font-semibold text-white mb-4">Create New Group</h2>
        <form onSubmit={handleCreateGroup}>
          <div className="mb-4">
            <label className="block text-white mb-2">Name</label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              className="w-full p-2 rounded bg-gray-700 text-white"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-white mb-2">Select Users</label>
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
              Create Group
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
