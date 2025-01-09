const ActivityDetailModal = ({ activity, isOpen, onClose }) => {
  if (!isOpen || !activity) return null;

  return (
    <div className="fixed inset-0 flex items-center justify-center z-50">
      <div className="absolute inset-0 bg-black opacity-70" onClick={onClose}></div>
      <div className="bg-customGray rounded-lg p-6 max-w-md w-full mx-4 z-10">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-semibold text-white">{activity.ACTIVITY_NAME}</h2>
          <button
            onClick={onClose}
            className="text-gray-400 hover:text-white"
          >
            ✕
          </button>
        </div>
        <div className="space-y-4">
          <p className="text-gray-300">{activity.DESCRIPTION}</p>
          <p className="text-sm text-gray-400">
            Date: {new Date(activity.DATE).toLocaleDateString()}
          </p>
          <p className="text-xs text-gray-500">
            Created by: {activity.CREATED_BY}
          </p>
          <p className="text-xs text-gray-500">
            Status: {activity.ACTIVITY_STATUS}
          </p>
        </div>
      </div>
    </div>
  );
};

export default ActivityDetailModal;