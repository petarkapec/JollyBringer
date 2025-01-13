import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { ThumbsUp } from 'lucide-react';
import useAuth from '../hooks/useAuth.js';

const Feedback = ({ activityId }) => {
  const { user } = useAuth();
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [isLiked, setIsLiked] = useState('Dislike');
  const backendUrl = import.meta.env.VITE_BACKEND_URL;

  useEffect(() => {
    const fetchComments = async () => {
      try {
        const response = await axios.get(`${backendUrl}/activities/${activityId}/feedbacks`, { withCredentials: true });
        setComments(response.data);
      } catch (error) {
        toast.error('Failed to fetch comments');
      }
    };

    fetchComments();
  }, [activityId]);

  const handleCommentSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(`${backendUrl}/activities/${activityId}/feedback`, {
        activity_id: activityId,
        participant_id: user.id,
        comment: newComment,
        is_liked: isLiked
      }, { withCredentials: true });
      setComments([...comments, response.data]);
      setNewComment('');
      setIsLiked('Dislike');
      toast.success('Comment added successfully');
    } catch (error) {
      toast.error('Failed to add comment');
    }
  };

  const handleLikeToggle = () => {
    setIsLiked(isLiked === 'Like' ? 'Dislike' : 'Like');
  };

  return (
    <div className="mt-4">
      <h3 className="text-lg font-semibold text-white mb-2">Feedback</h3>
      <form onSubmit={handleCommentSubmit} className="mb-4">
        <textarea
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
          className="w-full p-2 rounded bg-gray-700 text-white mb-2"
          rows="3"
          placeholder="Leave a comment"
          required
        />
        <div className="flex items-center gap-2">
          <button
            type="button"
            onClick={handleLikeToggle}
            className={`p-2 rounded ${isLiked === 'Like' ? 'bg-green-600' : 'bg-gray-600'} text-white`}
          >
            <ThumbsUp />
          </button>
          <button
            type="submit"
            className="px-4 py-2 bg-gray-600 text-white rounded-md hover:bg-gray-700"
          >
            Submit
          </button>
        </div>
      </form>
      <div className="space-y-4 max-h-64 overflow-y-auto">
        {comments.map((comment) => (
          <div key={comment.feedback_id} className="bg-gray-800 p-4 rounded-md">
            <p className="text-sm text-gray-300">{comment.comment}</p>
            <div className="flex items-center justify-between mt-2">
              <span className="text-xs text-gray-500">By: {comment.participant.username}</span>
              {comment.isLiked === 'Like' && <ThumbsUp className="text-green-500" />}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Feedback;