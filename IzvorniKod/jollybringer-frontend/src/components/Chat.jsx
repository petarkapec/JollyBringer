import React, { useState, useEffect } from "react";
import { createWebSocket } from "./websocket";
import '../styles/Chat.css';

const Chat = () => {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [socket, setSocket] = useState(null);

  useEffect(() => {
    const wsUrl = "ws://localhost:8080/chat"; // Replace with your WebSocket server URL
    const ws = createWebSocket(wsUrl, (message) => {
      setMessages((prevMessages) => [...prevMessages, message]); // Add the new message to the list
    });

    setSocket(ws);

    // Clean up the WebSocket connection on component unmount
    return () => {
      ws.close();
    };
  }, []);

  const sendMessage = () => {
    if (socket && socket.readyState === WebSocket.OPEN) {
      const message = {
        sender: "User1",
        content: newMessage,
        timestamp: new Date().toISOString(),
      };
      socket.send(JSON.stringify(message)); // Send the message
      setNewMessage(""); // Clear the input
    } else {
      console.warn("WebSocket is not connected");
    }
  };

  return (
    <div className="chat-container">
      <h2 className="chat-header">Chatroom</h2>
      <div className="messages-container">
        {messages.map((msg, index) => (
          <div key={index} className="message">
            <strong className="sender">{msg.sender}:</strong> 
            <span>{msg.content}</span> 
            <em className="timestamp">({msg.timestamp})</em>
          </div>
        ))}
      </div>
      <div className="input-container">
        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Type a message"
          className="input-message"
        />
        <button onClick={sendMessage} className="send-button">Send</button>
      </div>
    </div>
  );
};

export default Chat;
