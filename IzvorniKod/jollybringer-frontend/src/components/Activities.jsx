import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import ActivityCard from "./ActivityCard.jsx";
import CreateActivityModal from "./CreateActivityModal.jsx";
import ActivityDetailModal from "./ActivityDetailModal.jsx";

const Activities = ({ selectedGroup, role }) => {
  const [activities, setActivities] = useState([
    {
      "GROUP_ID": 6,
      "ID": 1,
      "ACTIVITY_NAME": "Decorate the Christmas Tree",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-01",
      "DESCRIPTION": "Decorate the Christmas tree with lights and ornaments.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 2,
      "ACTIVITY_NAME": "Bake Christmas Cookies",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-02",
      "DESCRIPTION": "Bake delicious Christmas cookies.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 3,
      "ACTIVITY_NAME": "Write Letters to Santa",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-03",
      "DESCRIPTION": "Write letters to Santa Claus.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 4,
      "ACTIVITY_NAME": "Watch a Christmas Movie",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-04",
      "DESCRIPTION": "Watch a classic Christmas movie.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 5,
      "ACTIVITY_NAME": "Make a Gingerbread House",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-05",
      "DESCRIPTION": "Build and decorate a gingerbread house.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 6,
      "ACTIVITY_NAME": "Go Ice Skating",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-06",
      "DESCRIPTION": "Go ice skating at a local rink.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 7,
      "ACTIVITY_NAME": "Read a Christmas Story",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-07",
      "DESCRIPTION": "Read a Christmas storybook.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 8,
      "ACTIVITY_NAME": "Make Hot Chocolate",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-08",
      "DESCRIPTION": "Make and enjoy hot chocolate.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 9,
      "ACTIVITY_NAME": "Sing Christmas Carols",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-09",
      "DESCRIPTION": "Sing Christmas carols with family and friends.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 10,
      "ACTIVITY_NAME": "Visit a Christmas Market",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-10",
      "DESCRIPTION": "Visit a local Christmas market.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 11,
      "ACTIVITY_NAME": "Make Christmas Crafts",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-11",
      "DESCRIPTION": "Create Christmas-themed crafts.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 12,
      "ACTIVITY_NAME": "Donate to Charity",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-12",
      "DESCRIPTION": "Donate to a local charity.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 13,
      "ACTIVITY_NAME": "Have a Christmas Photo Shoot",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-13",
      "DESCRIPTION": "Take festive photos with family.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 14,
      "ACTIVITY_NAME": "Make a Christmas Playlist",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-14",
      "DESCRIPTION": "Create a playlist of favorite Christmas songs.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 15,
      "ACTIVITY_NAME": "Wrap Christmas Gifts",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-15",
      "DESCRIPTION": "Wrap Christmas gifts for loved ones.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 16,
      "ACTIVITY_NAME": "Attend a Christmas Concert",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-16",
      "DESCRIPTION": "Attend a local Christmas concert.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 17,
      "ACTIVITY_NAME": "Make a Snowman",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-17",
      "DESCRIPTION": "Build a snowman in the yard.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 18,
      "ACTIVITY_NAME": "Have a Christmas Party",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-18",
      "DESCRIPTION": "Host a Christmas party with friends.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 19,
      "ACTIVITY_NAME": "Go Christmas Shopping",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-19",
      "DESCRIPTION": "Go shopping for Christmas gifts.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 20,
      "ACTIVITY_NAME": "Make Christmas Ornaments",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-20",
      "DESCRIPTION": "Create handmade Christmas ornaments.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 21,
      "ACTIVITY_NAME": "Read The Night Before Christmas",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-21",
      "DESCRIPTION": "Read the classic poem 'The Night Before Christmas'.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 22,
      "ACTIVITY_NAME": "Have a Christmas Game Night",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-22",
      "DESCRIPTION": "Play Christmas-themed games.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 23,
      "ACTIVITY_NAME": "Drive to See Christmas Lights",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-23",
      "DESCRIPTION": "Drive around to see Christmas light displays.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 24,
      "ACTIVITY_NAME": "Prepare Christmas Dinner",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-24",
      "DESCRIPTION": "Prepare a festive Christmas dinner.",
      "ACTIVITY_STATUS": "Pending"
    },
    {
      "GROUP_ID": 6,
      "ID": 25,
      "ACTIVITY_NAME": "Open Christmas Gifts",
      "CREATED_BY": "Admin",
      "DATE": "2023-12-25",
      "DESCRIPTION": "Open Christmas gifts with family.",
      "ACTIVITY_STATUS": "Pending"
    }
  ]);

  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [isPresident, setIsPresident] = useState(false);
  const [selectedActivity, setSelectedActivity] = useState(null);
  const [showDetailModal, setShowDetailModal] = useState(false);

  useEffect(() => {
    if (selectedGroup?.id) {
      fetchActivities();
    } else {
      setActivities([]);
      setIsPresident(false);
      setLoading(false);
    }
  }, [selectedGroup?.id]);

  const fetchActivities = async () => {
    try {
      // const response = await axios.get(`http://localhost:8080/groups/${selectedGroup.id}/activities`);
      // setActivities(response.data);
    } catch (error) {
      toast.error('Failed to fetch activities');
    } finally {
      setLoading(false);
    }
  };

  const handleActivityClick = (activity) => {
    setSelectedActivity(activity);
    setShowDetailModal(true);
  };

  if (!selectedGroup) {
    return (
      <div className="p-4">
        <h2 className="text-2xl font-bold mb-6">Christmas Activities</h2>
        <div className="p-4 text-center text-gray-400">
          Please select a group first
        </div>
      </div>
    );
  }

  if (loading) {
    return <div className="p-4 text-center text-white">Loading...</div>;
  }

  const days = Array.from({ length: 25 }, (_, i) => i + 1);

  return (
    <div className="p-4">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold">Christmas Activities</h2>
        {(role === 'President' || role === 'Admin') && (
          <button
            onClick={() => setShowModal(true)}
            className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700"
          >
            Create Activity
          </button>
        )}
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-4">
        {days.map((day) => (
          <ActivityCard
            key={day}
            day={day}
            activity={activities.find(a => 
              new Date(a.DATE).getDate() === day &&
              new Date(a.DATE).getMonth() === 11
            )}
            onClick={handleActivityClick}
          />
        ))}
      </div>
      <CreateActivityModal
        isOpen={showModal}
        onClose={() => setShowModal(false)}
        groupId={selectedGroup.id}
        onActivityCreated={fetchActivities}
      />
      <ActivityDetailModal
        activity={selectedActivity}
        isOpen={showDetailModal}
        onClose={() => setShowDetailModal(false)}
      />
    </div>
  );
};

export default Activities;