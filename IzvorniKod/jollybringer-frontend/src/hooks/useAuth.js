// src/hooks/useAuth.js
import { useEffect, useState } from 'react';
import axios from 'axios';

const useAuth = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(true);
  const [loading, setLoading] = useState(true);
  const [role, setRole] = useState('');
  const [groups, setGroups] = useState([]);
  const [user, setUser] = useState(null);
  const User = user;

  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await axios.get(`${import.meta.env.VITE_BACKEND_URL}/check-auth`, { withCredentials: true });
        setIsAuthenticated(response.data.isAuthenticated);
        setRole(response.data.role);
        setGroups(response.data.groups);
        setUser({
          id: response.data.user_id,
          username: response.data.username,
          email: response.data.email,
        });
      } catch (error) {
        setIsAuthenticated(false);
      } finally {
        setLoading(false);
      }
    };

    checkAuth();
  }, []);

  return { isAuthenticated, loading, role, groups, user };
};

export default useAuth;