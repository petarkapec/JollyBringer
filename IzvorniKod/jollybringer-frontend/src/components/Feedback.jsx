import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import { ThumbsUp, ThumbsDown } from 'lucide-react'; // Import ThumbsDown icon
import useAuth from '../hooks/useAuth.js';
import API from './api.js'; // Import the API class

const Feedback = ({ activityId }) => {
  const { user } = useAuth();
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [isLiked, setIsLiked] = useState('Dislike');

  useEffect(() => {
    const fetchComments = async () => {
      try {
        const response = await API.get(`/activities/${activityId}/feedbacks`); // Using API.get() instead of axios.get()
        setComments(response);
      } catch (error) {
        toast.error('Failed to fetch comments');
      }
    };

    fetchComments();
  }, [activityId]);

  const handleCommentSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await API.post(`/activities/${activityId}/feedback`, { // Using API.post() instead of axios.post()
        activity_id: activityId,
        participant_id: user.id,
        comment: newComment,
        is_liked: isLiked
      });
      setComments([...comments, response]);
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
      <h3 className="text-lg font-semibold text-red-700 mb-2">Feedback</h3>
      <form onSubmit={handleCommentSubmit} className="mb-4">
        <textarea
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
          className="placeholder-white w-full p-2 rounded bg-red-700 text-white mb-2"
          rows="3"
          placeholder="Leave a comment"
          required
        />
        <div className="flex items-center gap-2">
          <button
            type="button"
            onClick={handleLikeToggle}
            className={`p-2 rounded bg-green-600 ${isLiked === 'Like' ? 'bg-green-600' : 'bg-gray-600'} text-white`}
          >
            <ThumbsUp />
          </button>
          <button
            type="submit"
            className="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-gray-700"
          >
            Submit
          </button>
        </div>
      </form>
      <div className="space-y-4 max-h-64 overflow-y-auto">
        {comments.map((comment) => (
          <div key={comment.feedback_id} className="bg-red-700 p-4 rounded-md">
            <p className="text-sm text-white">{comment.comment}</p>
            <div className="flex items-center justify-between mt-2">
              <span className="text-xs text-white">By: {comment.participant.username}</span>
              {comment.isLiked === 'Like' ? (
                <ThumbsUp className="text-white" />
              ) : (
                <ThumbsDown className="text-white" />
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Feedback;
