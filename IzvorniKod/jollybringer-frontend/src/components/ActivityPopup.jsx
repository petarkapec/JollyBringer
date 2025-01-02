import React from 'react';
import '../styles/ActivityPopup.css';

const ActivityPopup = ({ activity, onClose }) => {
  if (!activity) return null;

  return (
    <div className="activity-popup-overlay" onClick={onClose}>
      <div className="activity-popup-content" onClick={(e) => e.stopPropagation()}>
        <button className="close-button" onClick={onClose}>X</button>
        <h3>{activity.ACTIVITY_NAME}</h3>
        <p>{activity.DESCRIPTION}</p>
        <p>Status: {activity.ACTIVITY_STATUS}</p>
        <p>Date: {new Date(activity.DATE).toLocaleDateString()}</p>
        <p>Created by: {activity.CREATED_BY}</p>
      </div>
    </div>
  );
};

export default ActivityPopup;