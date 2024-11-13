import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Login from "./Login.jsx";
import '../styles/App.css';
import Dashboard from "./Dashboard.jsx";
import ProtectedRoute from "./ProtectedRoute.jsx";

const App = () => {
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
      </Routes>
    </Router>
  );
};

export default App;