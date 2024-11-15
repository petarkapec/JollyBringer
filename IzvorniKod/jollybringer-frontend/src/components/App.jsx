import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./Login.jsx";
import '../styles/App.css';
import Dashboard from "./Dashboard.jsx";
import AdminDashboard from "./AdminDashboard.jsx";
import ProtectedRoute from "./ProtectedRoute.jsx";
import axios from 'axios';

const App = () => {
  const token = localStorage.getItem('authToken'); // Retrieve the token from local storage

  // Set the default Authorization header for all axios requests
  axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/dashboard/admin"
          element={
            <ProtectedRoute adminOnly={true}>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />
      </Routes>
    </Router>
  );
};

export default App;