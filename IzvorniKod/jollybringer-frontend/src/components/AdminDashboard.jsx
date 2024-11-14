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
        const groupsResponse = await axios.get('http://localhost:8080/admin/groups', { withCredentials: true });
        const usersResponse = await axios.get('http://localhost:8080/admin/users', { withCredentials: true });
        const applicationsResponse = await axios.get('http://localhost:8080/admin/applications', { withCredentials: true });

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
      await axios.post('http://localhost:8080/admin/approve', { userId }, { withCredentials: true });
      setApplications(applications.filter(application => application.id !== userId));
    } catch (error) {
      console.error('Error approving application:', error);
    }
  };

  return (
    <div className="admin-dashboard-container">
      <div className="admin-dashboard-header">
        <h1>Admin Dashboard</h1>
      </div>
      <div className="admin-dashboard-content">
        <section className="admin-dashboard-section">
          <h2>All Groups</h2>
          <ul>
            {groups.map(group => (
              <li key={group.id}>{group.name}</li>
            ))}
          </ul>
        </section>
        <section className="admin-dashboard-section">
          <h2>All Users</h2>
          <ul>
            {users.map(user => (
              <li key={user.id}>{user.username}</li>
            ))}
          </ul>
        </section>
        <section className="admin-dashboard-section">
          <h2>Applications for President</h2>
          <ul>
            {applications.map(application => (
              <li key={application.id}>
                {application.username}
                <button onClick={() => handleApproveApplication(application.id)}>Approve</button>
              </li>
            ))}
          </ul>
        </section>
      </div>
    </div>
  );
};

export default AdminDashboard;