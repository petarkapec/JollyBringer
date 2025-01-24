import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import Header from "./Header.jsx";
import API from './api.js';  // Import the API class

const AdminDashboard = () => {
  const [applications, setApplications] = useState([]);
  const [groups, setGroups] = useState([]);
  const [users, setUsers] = useState([]);
  const [activities, setActivities] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchAdminData = async () => {
      try {
        const groupsResponse = await API.get('/groups');  // Using API class
        const usersResponse = await API.get('/participants');  // Using API class
        const applicationsResponse = await API.get('/applications');  // Using API class
        const activitiesResponse = await API.get('/activities');  // Using API class
        setGroups(groupsResponse);
        setUsers(usersResponse);
        setApplications(applicationsResponse);
        setActivities(activitiesResponse);
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

  const handleDeleteActivity = async (activityId) => {
    try {
      await API.delete(`/activities/${activityId}`);  // Using API class
      setActivities(activities.filter(activity => activity.id !== activityId));
      toast.success('Activity deleted successfully');
    } catch (error) {
      console.error('Error deleting activity:', error);
      toast.error('Failed to delete activity');
    }
  };

  const truncateDescription = (description, maxLength) => {
    if (description.length > maxLength) {
      return description.substring(0, maxLength) + '...';
    }
    return description;
  };

  if (isLoading) return <div className="flex justify-center items-center h-full">Loading...</div>;

  return (
    <div className="w-dvw text-white bg-transparent shadow-2xl">
      <Header />
      <div className="p-8 bg-transparent">
        <h1 className="text-3xl font-bold mb-6">President Role Applications</h1>
        {applications.length === 0 ? (
          <p className="text-gray-400">No pending applications</p>
        ) : (
          <div className="bg-red-800 rounded-lg shadow overflow-hidden max-h-64">
            <div className="max-h-64 overflow-auto">
              <table className="min-w-full divide-y divide-red-700">
                <thead className="bg-red-900">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">User Name</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">User Email</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">Role</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">Actions</th>
                  </tr>
                </thead>
                <tbody className="bg-red-800 divide-y divide-red-700">
                  {applications.map((application) => (
                    <tr key={application.id}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{application.user.username}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{application.user.email}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{application.user.role.name}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <div className="flex gap-2">
                          <button onClick={() => handleApproveApplication(application.user.id)} className="bg-green-600 hover:bg-green-700 text-white px-3 py-1 rounded">Approve</button>
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
          <div className="bg-red-800 rounded-lg shadow overflow-hidden max-h-64">
            <div className="max-h-64 overflow-auto">
              <table className="min-w-full divide-y divide-red-700">
                <thead className="bg-red-900">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">Group Name</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">Group President</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">Actions</th>
                  </tr>
                </thead>
                <tbody className="bg-red-800 divide-y divide-red-700">
                  {groups.map((group) => (
                    <tr key={group.id}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{group.name}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{group.president.username}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <button onClick={() => handleDeleteGroup(group.id)} className="bg-red-600 hover:bg-red-700 text-white px-3 py-1 rounded">Delete</button>
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
          <div className="bg-red-800 rounded-lg shadow overflow-hidden max-h-64">
            <div className="max-h-64 overflow-auto">
              <table className="min-w-full divide-y divide-red-700">
                <thead className="bg-red-900">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">User Name</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">User Email</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">Actions</th>
                  </tr>
                </thead>
                <tbody className="bg-red-800 divide-y divide-red-700">
                  {users.map((user) => (
                    <tr key={user.id}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{user.username}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{user.email}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <button onClick={() => handleDeleteUser(user.id)} className="bg-red-600 hover:bg-red-700 text-white px-3 py-1 rounded">Delete</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        <h2 className="text-3xl font-bold mb-6 mt-8">Activities</h2>
        {activities.length === 0 ? (
          <p className="text-gray-400">No activities available</p>
        ) : (
          <div className="bg-red-800 rounded-lg shadow overflow-hidden max-h-64">
            <div className="max-h-64 overflow-auto">
              <table className="min-w-full divide-y divide-red-700">
                <thead className="bg-red-900">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">Activity Name</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">Description</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">Date</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-white">Actions</th>
                  </tr>
                </thead>
                <tbody className="bg-red-800 divide-y divide-red-700">
                  {activities.map((activity) => (
                    <tr key={activity.id}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{activity.activityName}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{truncateDescription(activity.description, 50)}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{new Date(activity.date).toLocaleDateString()}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <button onClick={() => handleDeleteActivity(activity.id)} className="bg-red-600 hover:bg-red-700 text-white px-3 py-1 rounded">Delete</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        <h2 className="text-3xl font-bold mb-6 mt-8">Activities</h2>
        {activities.length === 0 ? (
          <p className="text-gray-400">No activities available</p>
        ) : (
          <div className="bg-customGray rounded-lg shadow overflow-hidden max-h-64">
            <div className="max-h-64 overflow-auto">
              <table className="min-w-full divide-y divide-customGrayLighter">
                <thead className="bg-customGrayLighter">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Activity Name</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Description</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Date</th>
                    <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider">Actions</th>
                  </tr>
                </thead>
                <tbody className="bg-customGray divide-y divide-customGrayLighter">
                  {activities.map((activity) => (
                    <tr key={activity.id}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">{activity.activityName}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">{truncateDescription(activity.description, 50)}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">{new Date(activity.date).toLocaleDateString()}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <button onClick={() => handleDeleteActivity(activity.id)} className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded">Delete</button>
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
