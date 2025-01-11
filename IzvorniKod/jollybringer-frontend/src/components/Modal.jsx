import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../styles/Modal.css';
import useAuth from '../hooks/useAuth';

const Modal = ({ isVisible, onClose, role, updateGroups, onGroupCreated }) => {
  const { user } = useAuth();
  const [newGroupName, setNewGroupName] = useState('');
  const [selectedUsers, setSelectedUsers] = useState([]);
  const [allUsers, setAllUsers] = useState([]);

  useEffect(() => {
    if (role !== 'Participant') {
      const fetchUsers = async () => {
        try {
          const response = await axios.get('http://localhost:8080/participants/only', { withCredentials: true });
          setAllUsers(response.data);
        } catch (error) {
          console.error('Error fetching users:', error);
        }
      };
      fetchUsers();
    }
  }, [role]);

  const handleCreateGroup = async () => {
    try {
      const response = await axios.post('http://localhost:8080/groups', {
        name: newGroupName,
        users: selectedUsers
      }, { withCredentials: true });
      const newGroup = response.data;
      onClose();
      updateGroups(); // Update the groups list
      onGroupCreated(newGroup); // Set the newly created group as the selected group
    } catch (error) {
      console.error('Error creating group:', error);
    }
  };

  const handleUserSelection = (userId) => {
    setSelectedUsers(prevSelectedUsers =>
      prevSelectedUsers.includes(userId)
        ? prevSelectedUsers.filter(id => id !== userId)
        : [...prevSelectedUsers, userId]
    );
  };

  const handleApplyForPresident = async () => {
    try {
      await axios.post('http://localhost:8080/apply', {
        user_id: user.id,
        applied: true
      }, { withCredentials: true });
      onClose();
    } catch (error) {
      console.log('You have already applied for the role of Christmas president.');
      onClose();
    }
  };

  if (!isVisible) return null;

  const handleOverlayClick = (e) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <div className="modal-overlay" onClick={handleOverlayClick}>
      <div className="modal-content">
        <button className="modal-close-button" onClick={onClose}>X</button>
        {role === 'Participant' ? (
          <div className="participant-modal-content">
            <h2>Create a Group</h2>
            <p>To create a group, you need to be a Christmas president. Do you want to apply for that role?</p>
            <button onClick={handleApplyForPresident}>Apply</button>
          </div>
        ) : (
          <div className="new-group-form">
            <h2>Create New Group</h2>
            <input
              type="text"
              placeholder="Group Name"
              value={newGroupName}
              onChange={(e) => setNewGroupName(e.target.value)}
            />
            <h3>Select Users</h3>
            <ul>
              {allUsers.map(user => (
                <li key={user.id}>
                  <label>
                    <input
                      type="checkbox"
                      checked={selectedUsers.includes(user.id)}
                      onChange={() => handleUserSelection(user.id)}
                    />
                    {user.username}
                  </label>
                </li>
              ))}
            </ul>
            <button onClick={handleCreateGroup}>Create Group</button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Modal;