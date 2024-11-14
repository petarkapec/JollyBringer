// src/hooks/useAuth.js
import { useEffect, useState } from 'react';
import axios from 'axios';

const useAuth = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);
  const [role, setRole] = useState('');
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await axios.get('http://localhost:8080/check-auth', { withCredentials: true });
        setIsAuthenticated(response.data.isAuthenticated);
        setRole(response.data.role);
        setGroups(response.data.groups);
      } catch (error) {
        setIsAuthenticated(false);
      } finally {
        setLoading(false);
      }
    };

    checkAuth();
  }, []);

  return { isAuthenticated, loading, role, groups };
};

export default useAuth;