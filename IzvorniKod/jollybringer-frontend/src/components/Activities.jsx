import React, {useEffect, useState} from 'react';
import axios from 'axios';
import '../styles/Activities.css';
import ActivityPopup from './ActivityPopup';
import {Plus} from 'lucide-react';
import useAuth from '../hooks/useAuth';

const Activities = ({group}) => {
  const {role} = useAuth();
  const [activities, setActivities] = useState([]);
  const [currentDate, setCurrentDate] = useState(new Date('2023-12-13')); // Hardcoded date for testing
  const [flippedCards, setFlippedCards] = useState([]);
  const [selectedActivity, setSelectedActivity] = useState(null);
  const [isFormPopupOpen, setIsFormPopupOpen] = useState(false);
  const [newActivity, setNewActivity] = useState({
    activityName: '',
    description: '',
    date: '',
    activity_status: 'Pending',
    group: {id: group?.id},
    createdBy: 'Admin',
  });

  useEffect(() => {
    const fetchActivities = async () => {
      try {
        if (group) {
          const response = await axios.get(`http://localhost:8080/groups/${group.id}/activities`);
          setActivities(response.data);

          // Retrieve flipped cards from local storage
          const savedFlippedCards = JSON.parse(localStorage.getItem('flippedCards')) || [];
          setFlippedCards(savedFlippedCards);
        }
      } catch (error) {
        console.error('Error fetching activities:', error);
      }
    };

    fetchActivities();
  }, [group]);

  const isCardFlippable = (day) => {
    return currentDate.getDate() >= day && currentDate.getMonth() === 11; // December month check
  };

  const handleCardClick = (day) => {
    if (isCardFlippable(day) && !flippedCards.includes(day)) {
      const newFlippedCards = [...flippedCards, day];
      setFlippedCards(newFlippedCards);
      localStorage.setItem('flippedCards', JSON.stringify(newFlippedCards)); // Save to local storage
    } else if (flippedCards.includes(day)) {
      const activity = activities.find(act => new Date(act.date).getDate() === day);
      setSelectedActivity(activity);
    }
  };

  const handleClosePopup = () => {
    setSelectedActivity(null);
  };

  const handleInputChange = (e) => {
    const {name, value} = e.target;
    setNewActivity(prevState => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleCreateActivity = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(`http://localhost:8080/groups/${group.id}/activities`, newActivity);
      const createdActivity = response.data;
      setActivities(prevActivities => [...prevActivities, createdActivity]);
      setNewActivity({
        activityName: '',
        description: '',
        date: '',
        activity_status: 'Pending',
        group: {id: group.id},
        createdBy: 'Admin',
      });
      setIsFormPopupOpen(false);
    } catch (error) {
      console.error('Error creating activity:', error);
    }
  };

  if (!group) {
    return <div className="activities">Please select a group to view activities.</div>;
  }

  return (
    <div className="activities">
      <div className="create-activity-icon" onClick={() => setIsFormPopupOpen(true)}>
        <h2>Advent Calendar Activities</h2>
        <Plus size={32}/>
      </div>
      {isFormPopupOpen && (
        <div className="form-popup-overlay" onClick={() => setIsFormPopupOpen(false)}>
          <div className="form-popup-content" onClick={(e) => e.stopPropagation()}>
            <form className="create-activity-form" onSubmit={handleCreateActivity}>
              <h3>Create New Activity</h3>
              <input
                type="text"
                name="activityName"
                placeholder="Activity Name"
                value={newActivity.activityName}
                onChange={handleInputChange}
                required
              />
              <textarea
                name="description"
                placeholder="Description"
                value={newActivity.description}
                onChange={handleInputChange}
                required
              />
              <input
                type="date"
                name="date"
                value={newActivity.date}
                onChange={handleInputChange}
                required
              />
              <button type="submit">Create Activity</button>
            </form>
          </div>
        </div>
      )}
      <div className="advent-calendar">
        {Array.from({length: 25}, (_, index) => {
          const day = index + 1;
          const activity = activities.find(act => new Date(act.date).getDate() === day);
          const isFlipped = flippedCards.includes(day);
          const canBeFlipped = isCardFlippable(day);
          return (
            <div
              key={day}
              className={`card ${canBeFlipped ? 'flippable' : 'not-flippable'} ${isFlipped ? 'flipped' : ''}`}
              onClick={() => handleCardClick(day)}
            >
              <div className="card-front">
                <p>{day}</p>
              </div>
              <div className="card-back">
                {activity ? (
                  <>
                    <h3>{activity.activityName}</h3>
                  </>
                ) : (
                  <p>Activity hasn't been created yet</p>
                )}
              </div>
            </div>
          );
        })}
      </div>
      {selectedActivity && (
        <ActivityPopup
          activity={selectedActivity}
          onClose={handleClosePopup}
          onDelete={async () => {
            try {
              await axios.delete(`http://localhost:8080/activities/${selectedActivity.id}`, {withCredentials: true});
              setActivities(prevActivities => prevActivities.filter(activity => activity.id !== selectedActivity.id));
              handleClosePopup();
            } catch (error) {
              console.error('Error deleting activity:', error);
            }
          }}
        />
      )}
    </div>
  );
};

export default Activities;