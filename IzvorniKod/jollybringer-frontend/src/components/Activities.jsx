import React, { useEffect, useState } from 'react';
import '../styles/Activities.css';
import activitiesData from '../test_data/activitiesData';
import ActivityPopup from './ActivityPopup';

const Activities = () => {
  const [activities, setActivities] = useState([]);
  const [currentDate, setCurrentDate] = useState(new Date('2023-12-13')); // Hardcoded date for testing
  const [flippedCards, setFlippedCards] = useState([]);
  const [selectedActivity, setSelectedActivity] = useState(null);

  // useEffect(() => {
  //   const fetchActivities = async () => {
  //     try {
  //       const response = await axios.get('http://localhost:8080/activities', { withCredentials: true });
  //       setActivities(response.data);
  //     } catch (error) {
  //       console.error('Error fetching activities:', error);
  //     }
  //   };

  //   fetchActivities();
  // }, []);

  useEffect(() => {
    // hardcoded data for testing
    setActivities(activitiesData);

    // Retrieve flipped cards from local storage
    const savedFlippedCards = JSON.parse(localStorage.getItem('flippedCards')) || [];
    setFlippedCards(savedFlippedCards);
  }, []);

  const isCardFlippable = (day) => {
    return currentDate.getDate() >= day && currentDate.getMonth() === 11; // December month check
  };

  const handleCardClick = (day) => {
    if (isCardFlippable(day) && !flippedCards.includes(day)) {
      const newFlippedCards = [...flippedCards, day];
      setFlippedCards(newFlippedCards);
      localStorage.setItem('flippedCards', JSON.stringify(newFlippedCards)); // Save to local storage
    } else if (flippedCards.includes(day)) {
      const activity = activities.find(act => new Date(act.DATE).getDate() === day);
      setSelectedActivity(activity);
    }
  };

  const handleClosePopup = () => {
    setSelectedActivity(null);
  };

  return (
    <div className="activities">
      <h2>Advent Calendar Activities</h2>
      <div className="advent-calendar">
        {Array.from({ length: 25 }, (_, index) => {
          const day = index + 1;
          const activity = activities.find(act => new Date(act.DATE).getDate() === day);
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
                  <h3>{activity.ACTIVITY_NAME}</h3>
                ) : (
                  <p>Activity hasn't been created yet</p>
                )}
              </div>
            </div>
          );
        })}
      </div>
      {selectedActivity && <ActivityPopup activity={selectedActivity} onClose={handleClosePopup} />}
    </div>
  );
};

export default Activities;