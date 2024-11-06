import React, { useState, useEffect } from 'react';
import '../styles/Dashboard.css';
import Activities from "./Activities.jsx";
import Chat from "./Chat.jsx";
import CountdownTimer from "./CountdownTimer.jsx";

const Dashboard = () => {

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <div>Jolly Bringer</div>
        <CountdownTimer page={'Dashboard'}/>
        <div className='dashboard-header-user-data'>
          <div>Role: User</div>
          <button className="dashboard-header-button">Logout</button>
        </div>
      </header>
      <div className="dashboard-content">
        <Activities />
        <Chat />
      </div>
    </div>
  );
};

export default Dashboard;
