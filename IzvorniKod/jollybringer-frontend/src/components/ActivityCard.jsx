import React, { useState, useEffect } from 'react';
import ActivityDetailModal from './ActivityDetailModal';

const ActivityCard = ({ day, activity, onClick, onActivityDeleted }) => {
  const [isFlipped, setIsFlipped] = useState(false);
  const [showDetailModal, setShowDetailModal] = useState(false);
  const currentDate = new Date('12.25.2024.');
  const cardDate = new Date(currentDate.getFullYear(), 11, day); // December of the current year
  const isEligible = currentDate >= cardDate;

  useEffect(() => {
    const flippedCards = JSON.parse(localStorage.getItem('flippedCards')) || {};
    if (flippedCards[day]) {
      setIsFlipped(true);
    }
  }, [day]);

  const handleFlip = () => {
    if (isEligible) {
      const flippedCards = JSON.parse(localStorage.getItem('flippedCards')) || {};
      flippedCards[day] = true;
      localStorage.setItem('flippedCards', JSON.stringify(flippedCards));
      setIsFlipped(true);
    }
  };

  const handleClick = () => {
    if (isFlipped) {
      setShowDetailModal(true);
    } else {
      handleFlip();
    }
  };

  return (
    <>
      <div 
        className={`p-4 rounded-lg ${isFlipped ? 'bg-customGrayLighter' : 'bg-customGray'} ${isEligible ? 'cursor-pointer' : 'cursor-not-allowed'}`}
        onClick={handleClick}
        style={{ cursor: isEligible ? 'pointer' : 'not-allowed' }}
      >
        <div className="text-xl font-bold mb-2 text-red-600">Day {day}</div>
        {isFlipped ? (
          activity ? (
            <h3 className="text-lg font-semibold text-gray-300 text-[16px]">
              {activity.activityName}
            </h3>
          ) : (
            <p className="text-gray-400">No activity planned</p>
          )
        ) : (
          <p className="text-gray-400">Click to reveal</p>
        )}
      </div>
      {activity && (
        <ActivityDetailModal
          activity={activity}
          isOpen={showDetailModal}
          onClose={() => setShowDetailModal(false)}
          onActivityDeleted={onActivityDeleted}
        />
      )}
    </>
  );
};

export default ActivityCard;