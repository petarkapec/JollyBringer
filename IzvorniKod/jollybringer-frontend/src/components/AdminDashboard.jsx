import { useEffect, useState } from 'react';
import axios from 'axios';
import '../styles/AdminDashboard.css';

const AdminDashboard = () => {
  const [groups, setGroups] = useState([]);
  const [users, setUsers] = useState([]);
  const [applications, setApplications] = useState([]);

  useEffect(() => {
    const fetchAdminData = async () => {
      try {
        const groupsResponse = await axios.get('http://localhost:8080/groups', { withCredentials: true });
        const usersResponse = await axios.get('http://localhost:8080/participants', { withCredentials: true });
        const applicationsResponse = await axios.get('http://localhost:8080/applications', { withCredentials: true });
        setGroups(groupsResponse.data);
        setUsers(usersResponse.data);
        setApplications(applicationsResponse.data);
      } catch (error) {
        console.error('Error fetching admin data:', error);
      }
    };

    fetchAdminData();
  }, []);

  const handleApproveApplication = async (userId) => {
    try {
      await axios.post('http://localhost:8080/approve', {
        user_id: userId,
        applied: true
      }, { withCredentials: true });
      setApplications(applications.filter(application => application.user.id !== userId));
    } catch (error) {
      console.error('Error approving application:', error);
    }
  };

  const handleReturnToDashboard = () => {
    window.location.href = '/dashboard';
  };

  return (
    <div className="admin-dashboard-container">
      <div className="admin-dashboard-header">
        <h1>Admin Dashboard</h1>
        <button className="return-button" onClick={handleReturnToDashboard}>Return to Dashboard</button>
      </div>
      <div className="admin-dashboard-content">
        <section className="admin-dashboard-section groups">
          <h2>All Groups</h2>
          <ul>
            {groups.map(group => (
              <li key={group.id}>{group.name}</li>
            ))}
          </ul>
        </section>
        <section className="admin-dashboard-section users">
          <h2>All Users</h2>
          <ul>
            {users.map(user => (
              <li key={user.id}>{user.username}</li>
            ))}
          </ul>
        </section>
        <section className="admin-dashboard-section applications">
          <h2>Applications for President</h2>
          <ul>
            {applications.map(application => (
              <li key={application.id}>
                {application.user.username}
                <button onClick={() => handleApproveApplication(application.user.id)}>Approve</button>
              </li>
            ))}
          </ul>
        </section>
      </div>
    </div>
  );
};

export default AdminDashboard;