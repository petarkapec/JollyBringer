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

  return (
    <div className="admin-dashboard-container">
      <h1>Admin Dashboard</h1>
      <section>
        <h2>All Groups</h2>
        <ul>
          {groups.map(group => (
            <li key={group.id}>{group.name}</li>
          ))}
        </ul>
      </section>
      <section>
        <h2>All Users</h2>
        <ul>
          {users.map(user => (
            <li key={user.id}>{user.username}</li>
          ))}
        </ul>
      </section>
      <section>
        <h2>Applications for President</h2>
        <ul>
          {applications.map(application => (
            <li key={application.id}>{application.username} - {application.status}</li>
          ))}
        </ul>
      </section>
    </div>
  );
};

export default AdminDashboard;