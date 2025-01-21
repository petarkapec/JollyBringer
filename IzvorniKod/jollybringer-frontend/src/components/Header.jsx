import React, {useEffect, useRef, useState} from 'react';
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import CountdownTimer from "./CountdownTimer.jsx";
import {Menu} from "lucide-react";
import useAuth from "../hooks/useAuth.js";
import CreateGroupModal from "./CreateGroupModal.jsx";
import RoleModal from "./RoleModal.jsx";
import API from "./api.js"; // Importing the API class

const SELECTED_GROUP_KEY = 'selectedGroup';

const Header = ({ onGroupSelect }) => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const { role, user, loading } = useAuth();
  const [showRoleModal, setShowRoleModal] = useState(false);
  const menuRef = useRef(null);
  const [showGroupModal, setShowGroupModal] = useState(false);
  const [userGroups, setUserGroups] = useState([]);
  const [selectedGroup, setSelectedGroup] = useState();

  useEffect(() => {
    const handleClickOutside = (event) => {
      // Ignore clicks on the Menu icon itself
      if (event.target.closest('[data-menu-icon]')) {
        return;
      }

      if (menuRef.current && !menuRef.current.contains(event.target)) {
        setIsMenuOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [menuRef]);

  const fetchUserGroups = async () => {
    if (!user) return; // Add this check to ensure user is defined

    try {
      const groups = await API.get('/groups');
      const userGroups = groups.filter(group => group.members.some(member => member.id === user.id));
      setUserGroups(userGroups);

      const storedGroup = JSON.parse(localStorage.getItem(SELECTED_GROUP_KEY));
      if (storedGroup && !userGroups.some(group => group.id === storedGroup.id)) {
        localStorage.removeItem(SELECTED_GROUP_KEY);
        setSelectedGroup(null);
        onGroupSelect(null);
      }
    } catch (error) {
      console.error('Error fetching user groups:', error);
    }
  };

  useEffect(() => {
    fetchUserGroups();
  }, [user]);

  useEffect(() => {
    const storedGroup = localStorage.getItem(SELECTED_GROUP_KEY);
    if (storedGroup) {
      setSelectedGroup(JSON.parse(storedGroup).name);
    }
  }, []);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const handleNewGroup = () => {
    if (role !== 'President' && role !== 'Admin') {
      setShowRoleModal(true);
      setIsMenuOpen(false);
    } else {
      setShowGroupModal(true);
      setIsMenuOpen(false);
    }
  };

  const handleLogout = () => {
    window.location.href = '/';
  };

  const handleApplyForPresident = async () => {
    try {
      await API.post('/apply', {  // Replaced axios.post with API.post
        user_id: user.id,
        applied: true
      });
      setShowRoleModal(false);
      toast.success('Application submitted successfully!');
    } catch (error) {
      console.log('You have already applied for the role of Christmas president.');
      setShowRoleModal(false);
    }
  };

  const handleAdminRedirect = () => {
    window.location.href = '/dashboard/admin';
    setIsMenuOpen(false); // Close menu after clicking
  };

  const handleGroupClick = (group) => {
    onGroupSelect(group);
    setIsMenuOpen(false);
  };

  return (
    <>
      <div className={'text-white flex items-center justify-between h-12 border-b py-[34px] px-5 bg-black'}>
        <h1 className={'text-3xl hover:cursor-pointer'} onClick={() => {
          window.location.href = "/dashboard";
        }}>Jollybringer</h1>
        <CountdownTimer page />
        <div className={`flex items-center gap-10`}>
          <p className={'text-[16px]'}>Role: {role}</p>
          {selectedGroup && (
            <p className={'text-[16px]'}>Group: {selectedGroup}</p>
          )}
          <Menu
            data-menu-icon
            className={`size-8 cursor-pointer ${isMenuOpen ? 'text-red-600' : ''}`}
            onClick={toggleMenu}
          />
          <button className={'text-2xl'} onClick={handleLogout}>
            Logout
          </button>
        </div>
        {isMenuOpen && (
          <div ref={menuRef}
               className={'absolute top-[71px] right-16 border shadow-md rounded-md p-4 backdrop-blur-md'}>
            <ul className={'flex gap-2 flex-col'}>
              {userGroups
                .filter(group => group.name !== 'NO_GROUP')
                .map((group) => (
                  <li
                    key={group.id}
                    onClick={() => handleGroupClick(group)}
                    className={'py-2 px-4 hover:bg-gray-200 cursor-pointer bg-white rounded-[6px] text-black text-center'}
                  >
                    {group.name}
                  </li>
                ))}
              {userGroups.length === 0 && (
                <>
                  <hr />
                  <li onClick={handleNewGroup}
                      className={'py-2 px-4 hover:bg-gray-200 cursor-pointer bg-white rounded-[6px] text-black text-center'}>
                    New group
                  </li>
                </>
              )}
              {role === 'Admin' && (
                <>
                  <hr />
                  <li onClick={handleAdminRedirect}
                      className={'py-2 px-4 hover:bg-gray-200 cursor-pointer bg-white rounded-[6px] text-black text-center'}>
                    Admin
                  </li>
                </>
              )}
            </ul>
          </div>
        )}
      </div>
      {showGroupModal && (
        <CreateGroupModal
          isOpen={showGroupModal}
          onClose={() => setShowGroupModal(false)}
          onGroupCreated={() => fetchUserGroups()}  // Add this prop
        />
      )}
      <RoleModal
        isOpen={showRoleModal}
        onClose={() => setShowRoleModal(false)}
        onApply={handleApplyForPresident}
      />

      <ToastContainer
        position="bottom-right"
        autoClose={5000}
        hideProgressBar={false}
        closeOnClick
        pauseOnHover
        theme="dark"
      />
    </>
  );
};

export default Header;
