import { useState, useEffect } from 'react';
import axios from 'axios';

const useAuth = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);
  const [role, setRole] = useState(null);
  const [groups, setGroups] = useState([]);
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('authToken');
    console.log("Token:", token);

    if (token) {
      axios
        .get(`${import.meta.env.VITE_BACKEND_URL}/check-auth`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((response) => {
          setIsAuthenticated(true);
          setRole(response.data.role); 
          setGroups(response.data.groups); // Postavi grupe
          setUser({
            id: response.data.user_id,
            username: response.data.username,
            email: response.data.email,
          }); // Postavi korisnika
        })
        .catch((error) => {
          console.error('Error fetching user data:', error);
          setIsAuthenticated(false);
        })
        .finally(() => {
          setLoading(false);
        });
    } else {
      setIsAuthenticated(false);
      setLoading(false);
    }
  }, []);

  return { isAuthenticated, loading, role, groups, user };
};

export default useAuth;
