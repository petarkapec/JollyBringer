import React from 'react';
import '../styles/Modal.css';

const Modal = ({ isVisible, onClose }) => {
  if (!isVisible) return null;

  const handleOverlayClick = (e) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <div className="modal-overlay" onClick={handleOverlayClick}>
      <div className="modal-content">
        <h2>Create a Group</h2>
        <p>To create a group, you need to be a Christmas president. Do you want to apply for that role?</p>
        <button onClick={onClose}>Apply</button>
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

export default Modal;