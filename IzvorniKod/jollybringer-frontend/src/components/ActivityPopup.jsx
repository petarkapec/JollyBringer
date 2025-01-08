import React from 'react';
import '../styles/ActivityPopup.css';

const ActivityPopup = ({ activity, onClose, onDelete }) => {
  if (!activity) return null;

  return (
    <div className="activity-popup-overlay" onClick={onClose}>
      <div className="activity-popup-content" onClick={(e) => e.stopPropagation()}>
        <button className="close-button" onClick={onClose}>X</button>
        <h3>{activity.activityName}</h3>
        <p>{activity.description}</p>
        <p>Status: {activity.activity_status}</p>
        <p>Date: {new Date(activity.date).toLocaleDateString()}</p>
        <p>Created by: {activity.createdBy}</p>
        <button className="delete-button" onClick={onDelete}>Delete</button>
      </div>
    </div>
  );
};

export default ActivityPopup;