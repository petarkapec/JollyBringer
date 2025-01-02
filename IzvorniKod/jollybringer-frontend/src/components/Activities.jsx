import React, { useEffect, useState } from 'react';
import '../styles/Activities.css';
import ActivityPopup from './ActivityPopup';
import activitiesData from '../test_data/activitiesData'; // Import the hardcoded data

const Activities = ({ group }) => {
  const [activities, setActivities] = useState([]);
  const [currentDate, setCurrentDate] = useState(new Date('2023-12-13')); // Hardcoded date for testing
  const [flippedCards, setFlippedCards] = useState([]);
  const [selectedActivity, setSelectedActivity] = useState(null);

  useEffect(() => {
    if (group) {
      // Filter activities for the selected group
      const groupActivities = activitiesData.filter(activity => activity.GROUP_ID === group.id);
      setActivities(groupActivities);

      // Retrieve flipped cards from local storage
      const savedFlippedCards = JSON.parse(localStorage.getItem('flippedCards')) || [];
      setFlippedCards(savedFlippedCards);
    }
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
      const activity = activities.find(act => new Date(act.DATE).getDate() === day);
      setSelectedActivity(activity);
    }
  };

  const handleClosePopup = () => {
    setSelectedActivity(null);
  };

  if (!group) {
    return <div className="activities">Please select a group to view activities.</div>;
  }

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