import {Navigate} from 'react-router-dom';
import useAuth from '../hooks/useAuth';

const ProtectedRoute = ({children}) => {
  const {isAuthenticated, loading} = useAuth();

  if (loading) {
    return <div>Loading...</div>;
  }

  return isAuthenticated ? children : <Navigate to="/"/>;
};

export default ProtectedRoute;