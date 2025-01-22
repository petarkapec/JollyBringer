import React, {useEffect, useState} from 'react';
import {toast} from 'react-toastify';
import ActivityCard from "./ActivityCard.jsx";
import CreateActivityModal from "./CreateActivityModal.jsx";
import ActivityDetailModal from "./ActivityDetailModal.jsx";
import useAuth from "../hooks/useAuth.js";
import API from './api.js'; // Import the API class
import {SquareArrowOutUpRight} from 'lucide-react'; // Import the icon
import {useLocation, useNavigate} from 'react-router-dom'; // Import useLocation and useNavigate

const Activities = ({ selectedGroup, role }) => {
  const [activities, setActivities] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [isPresident, setIsPresident] = useState(false);
  const [selectedActivity, setSelectedActivity] = useState(null);
  const [showDetailModal, setShowDetailModal] = useState(false);
  const location = useLocation(); // Get the current URL
  const navigate = useNavigate(); // Get the navigate function

  useAuth();

  useEffect(() => {
    if (selectedGroup?.id) {
      fetchActivities();
    } else {
      setActivities([]);
      setIsPresident(false);
      setLoading(false);
    }
  }, [selectedGroup?.id]);

  // Fetch activities using the API class
  const fetchActivities = async () => {
    try {
      const data = await API.get(`/groups/${selectedGroup.id}/activities`);  // Using API class
      setActivities(data);
    } catch (error) {
      toast.error('Failed to fetch activities');
    } finally {
      setLoading(false);
    }
  };

  // Handle activity click and open details
  const handleActivityClick = (activity) => {
    setSelectedActivity(activity);
    setShowDetailModal(true);
  };

  // Delete activity using the API class
  const handleActivityDeleted = async (activityId) => {
    try {
      await API.delete(`/activities/${activityId}`);  // Using API class
      setActivities((prevActivities) => prevActivities.filter(activity => activity.id !== activityId));
    } catch (error) {
      toast.error('Failed to delete activity');
    }
  };

  // Create activity with AI
  const handleCreateWithAI = async () => {
    try {
      const createdActivity = await API.get(`/ai/create-activity/${selectedGroup.id}`);
      const createdDate = new Date(createdActivity.date).getDate();
      toast.success(`Activity created with AI successfully on day ${createdDate}`);
      fetchActivities(); // Refresh activities after creating with AI
    } catch (error) {
      toast.error('Failed to create activity with AI');
    }
  };

  if (!selectedGroup) {
    return (
      <div className="p-4">
        <h2 className="text-2xl font-bold mb-6 text-white">Christmas Activities</h2>
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
        <h2 className="text-2xl font-bold text-white">Christmas Activities</h2>
        <div className="flex gap-2 items-center">
          {location.pathname !== '/dashboard/activities' && (
            <SquareArrowOutUpRight
              className="cursor-pointer text-white hover:text-gray-300 mr-3"
              onClick={() => navigate('/dashboard/activities', { state: { selectedGroupId: selectedGroup.id } })}
            />
          )}
          {(role === 'President' || role === 'Admin') && (
            <>
              <button
                onClick={handleCreateWithAI} 
                className="flex flex-col items-center cursor-pointer">
                <img src="\assets\img\elf.png" alt="Elf Icon" className="w-16 h-16" />
                <span className='text-white'>Create with AI</span>
              </button>

              <button
                onClick={() => setShowModal(true)}
                className="flex flex-col items-center cursor-pointer"
              >
                <img src='/assets/img/gingerbread.png' className='w-16 h-16 md:w-16 md:h-16'></img>
                <span className='text-white'>Create Activity</span>
              </button>
            </>
          )}
        </div>
      </div>
      <div className="grid grid-cols-2 md:grid-cols-5 gap-4 min-h-screen">

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
