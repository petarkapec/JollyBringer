import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import Header from "./Header.jsx";
import API from './api.js';  // Import the API class

const AdminDashboard = () => {
  const [applications, setApplications] = useState([]);
  const [groups, setGroups] = useState([]);
  const [users, setUsers] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchAdminData = async () => {
      try {
        const groupsResponse = await API.get('/groups');  // Using API class
        const usersResponse = await API.get('/participants');  // Using API class
        const applicationsResponse = await API.get('/applications');  // Using API class
        setGroups(groupsResponse);
        setUsers(usersResponse);
        setApplications(applicationsResponse);
      } catch (error) {
        console.error('Error fetching admin data:', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchAdminData();
  }, []);

  const handleApproveApplication = async (userId) => {
    try {
      await API.post('/approve', {
        user_id: userId,
        applied: true
      });  // Using API class
      setApplications(applications.filter(application => application.user.id !== userId));
      toast.success('Application approved');
    } catch (error) {
      console.error('Error approving application:', error);
      toast.error('Failed to approve application');
    }
  };

  const handleDeleteGroup = async (groupId) => {
    try {
      await API.delete(`/groups/${groupId}`);  // Using API class
      setGroups(groups.filter(group => group.id !== groupId));
      toast.success('Group deleted successfully');
    } catch (error) {
      console.error('Error deleting group:', error);
      toast.error('Failed to delete group');
    }
  };

  const handleDeleteUser = async (userId) => {
    try {
      await API.delete(`/participants/${userId}`);  // Using API class
      setUsers(users.filter(user => user.id !== userId));
      toast.success('User deleted successfully');
    } catch (error) {
      console.error('Error deleting user:', error);
      toast.error('Failed to delete user');
    }
  };

  if (isLoading) return <div className="flex justify-center items-center h-full">Loading...</div>;

  return (
    <div className="h-dvh w-dvw text-white">
      <Header />
      <div className="p-8">
        <h1 className="text-3xl font-bold mb-6">President Role Applications</h1>
        {applications.length === 0 ? (
          <p className="text-gray-400">No pending applications</p>
        ) : (
          <div className="bg-customGray rounded-lg shadow overflow-hidden max-h-64">
            <div className="max-h-64 overflow-auto">
              <table className="min-w-full divide-y divide-customGrayLighter">
                <thead className="bg-customGrayLighter">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">User Name</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">User Email</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Role</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Actions</th>
                  </tr>
                </thead>
                <tbody className="bg-customGray divide-y divide-customGrayLighter">
                  {applications.map((application) => (
                    <tr key={application.id}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">{application.user.username}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">{application.user.email}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">{application.user.role.name}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <div className="flex gap-2">
                          <button onClick={() => handleApproveApplication(application.user.id)} className="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded">Approve</button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        <h2 className="text-3xl font-bold mb-6 mt-8">Groups</h2>
        {groups.length === 0 ? (
          <p className="text-gray-400">No groups available</p>
        ) : (
          <div className="bg-customGray rounded-lg shadow overflow-hidden max-h-64">
            <div className="max-h-64 overflow-auto">
              <table className="min-w-full divide-y divide-customGrayLighter">
                <thead className="bg-customGrayLighter">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Group Name</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Group President</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Actions</th>
                  </tr>
                </thead>
                <tbody className="bg-customGray divide-y divide-customGrayLighter">
                  {groups.map((group) => (
                    <tr key={group.id}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">{group.name}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">{group.president.username}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <button onClick={() => handleDeleteGroup(group.id)} className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded">Delete</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        <h2 className="text-3xl font-bold mb-6 mt-8">Users</h2>
        {users.length === 0 ? (
          <p className="text-gray-400">No users available</p>
        ) : (
          <div className="bg-customGray rounded-lg shadow overflow-hidden max-h-64">
            <div className="max-h-64 overflow-auto">
              <table className="min-w-full divide-y divide-customGrayLighter">
                <thead className="bg-customGrayLighter">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">User Name</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">User Email</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Actions</th>
                  </tr>
                </thead>
                <tbody className="bg-customGray divide-y divide-customGrayLighter">
                  {users.map((user) => (
                    <tr key={user.id}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">{user.username}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">{user.email}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <button onClick={() => handleDeleteUser(user.id)} className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded">Delete</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminDashboard;
