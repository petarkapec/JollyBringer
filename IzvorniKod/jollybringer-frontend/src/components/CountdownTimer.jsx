import React, { useState, useEffect } from 'react'
import '../styles/CountdownTimer.css'
import calculateTimeLeft from "../utils/functions.js"

const CountdownTimer = ({page}) => {

  const [timeLeft, setTimeLeft] = useState(calculateTimeLeft())

  useEffect(() => {
    const timer = setTimeout(() => {
      setTimeLeft(calculateTimeLeft())
    }, 1000)

    return () => clearTimeout(timer)
  }, [timeLeft])

  return (
    <div className={page === 'Login' ? 'login-countdown-wrapper' : 'countdown-wrapper'}>
      {Object.keys(timeLeft).length ? (
        <div>
          {Object.keys(timeLeft).map((interval) => (
            <span key={interval}>
              {timeLeft[interval]}{interval}{" "}
            </span>
          ))} to <span>Christmas</span>!
        </div>
      ) : (
        <span>Merry Christmas!</span>
      )}
    </div>
  )
}

export default CountdownTimer;