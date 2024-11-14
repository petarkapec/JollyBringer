const calculateTimeLeft = () => {
  const now = new Date()
  const currentYear = now.getFullYear()
  const nextChristmas = new Date(`December 25, ${currentYear}`)

  if (now > nextChristmas) {
    nextChristmas.setFullYear(currentYear + 1)
  }

  const difference = +nextChristmas - +now
  let timeLeft = {}

  if (now.getMonth() === 11 && now.getDate() === 25) {
    // It's December 25
    return timeLeft
  }

  if (difference > 0) {
    timeLeft = {
      d: Math.floor(difference / (1000 * 60 * 60 * 24)),
      h: Math.floor((difference / (1000 * 60 * 60)) % 24),
      m: Math.floor((difference / 1000 / 60) % 60),
      s: Math.floor((difference / 1000) % 60),
    }
  }

  return timeLeft
}

export default calculateTimeLeft