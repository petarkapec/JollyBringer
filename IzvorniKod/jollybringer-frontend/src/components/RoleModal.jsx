'use client'

import React from 'react';

const RoleModal = ({ isOpen, onClose, onApply }) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-[9999]">
      <div className="bg-customGray rounded-lg p-6 max-w-md w-full mx-4 z-[9999]">
        <h2 className="text-xl font-semibold text-white mb-4">Permission Required</h2>
        <p className="text-gray-300 mb-6">
          Only Presidents and Admins can create new groups. Would you like to apply for the President role?
        </p>
        <div className="flex justify-end gap-4">
          <button
            onClick={onClose}
            className="px-4 py-2 text-white hover:text-gray-300">
            Cancel
          </button>
          <button
            onClick={onApply}
            className="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
          >
            Apply for President
          </button>
        </div>
      </div>
    </div>
  );
};

export default RoleModal;