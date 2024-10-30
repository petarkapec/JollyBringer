// src/Dashboard.js
import React from 'react';
import './Dashboard.css';

function Dashboard() {
    return (
        <div className="dashboard-container">
            <h1 className="dashboard-title">Jolly Bringer Dashboard</h1>
            <div className="widgets-container">
                <div className="widget">
                    <h2>Aktivnosti</h2>
                    <p>Ovdje će se prikazivati aktivnosti...</p>
                </div>
                <div className="widget">
                    <h2>Chat</h2>
                    <p>Ovdje će se prikazivati chat poruke...</p>
                </div>
            </div>
        </div>
    );
}

export default Dashboard;
