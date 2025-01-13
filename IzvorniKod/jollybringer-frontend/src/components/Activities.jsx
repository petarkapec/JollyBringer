import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import ActivityCard from "./ActivityCard.jsx";
import CreateActivityModal from "./CreateActivityModal.jsx";
import ActivityDetailModal from "./ActivityDetailModal.jsx";
import useAuth from "../hooks/useAuth.js";

const Activities = ({ selectedGroup, role }) => {
  const [activities, setActivities] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [isPresident, setIsPresident] = useState(false);
  const [selectedActivity, setSelectedActivity] = useState(null);
  const [showDetailModal, setShowDetailModal] = useState(false);

  const backendUrl = import.meta.env.VITE_BACKEND_URL;

  useAuth()

  useEffect(() => {
    if (selectedGroup?.id) {
      fetchActivities();
    } else {
      setActivities([]);
      setIsPresident(false);
      setLoading(false);
    }
  }, [selectedGroup?.id]);

  const fetchActivities = async () => {
    try {
       const response = await axios.get(`${backendUrl}/groups/${selectedGroup.id}/activities`, {withCredentials: true});
       setActivities(response.data);
    } catch (error) {
      toast.error('Failed to fetch activities');
    } finally {
      setLoading(false);
    }
  };

  const handleActivityClick = (activity) => {
    setSelectedActivity(activity);
    setShowDetailModal(true);
  };

  const handleActivityDeleted = async (activityId) => {
    await axios.delete(`${backendUrl}/activities/${activityId}`, { withCredentials: true });
    setActivities((prevActivities) => prevActivities.filter(activity => activity.id !== activityId));
  };

  const handleCreateWithAI = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/ai/create-activity/${selectedGroup.id}`, { withCredentials: true });
      const newActivity = response.data;
      setActivities((prevActivities) => [...prevActivities, newActivity]);
      toast.success('Activity created with AI successfully');
    } catch (error) {
      toast.error('Failed to create activity with AI');
    }
  };

  if (!selectedGroup) {
    return (
      <div className="p-4">
        <h2 className="text-2xl font-bold mb-6">Christmas Activities</h2>
        <div className="p-4 text-center text-gray-400">
          Please select a group first
        </div>
      </div>
    );
  }

  if (loading) {
    return <div className="p-4 text-center text-white">Loading...</div>;
  }

  const days = Array.from({ length: 25 }, (_, i) => i + 1);

  return (
    <div className="p-4">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold">Christmas Activities</h2>
        <div className="flex gap-2">
          {(role === 'President' || role === 'Admin') && (
            <>
              <button
                onClick={handleCreateWithAI}
                className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700"
              >
                Create with AI
              </button>
              <button
                onClick={() => setShowModal(true)}
                className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700"
              >
                Create Activity
              </button>
            </>
          )}
        </div>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-4">
        {days.map((day) => (
          <ActivityCard
            key={day}
            day={day}
            activity={activities.find(a => 
              new Date(a.date).getDate() === day &&
              new Date(a.date).getMonth() === 11
            )}
            onClick={handleActivityClick}
            onActivityDeleted={handleActivityDeleted}
          />
        ))}
      </div>
      <CreateActivityModal
        isOpen={showModal}
        onClose={() => setShowModal(false)}
        groupId={selectedGroup.id}
        onActivityCreated={fetchActivities}
      />
      <ActivityDetailModal
        activity={selectedActivity}
        isOpen={showDetailModal}
        onClose={() => setShowDetailModal(false)}
      />
    </div>
  );
};

export default Activities;