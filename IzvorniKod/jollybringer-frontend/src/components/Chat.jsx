import React, { useState, useEffect } from "react";
import { createWebSocket } from "./websocket";

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
    <div>
      <h2>Chatroom</h2>
      <div style={{ border: "1px solid #ccc", height: "300px", overflowY: "scroll" }}>
        {messages.map((msg, index) => (
          <div key={index}>
            <strong>{msg.sender}:</strong> {msg.content} <em>({msg.timestamp})</em>
          </div>
        ))}
      </div>
      <input
        type="text"
        value={newMessage}
        onChange={(e) => setNewMessage(e.target.value)}
        placeholder="Type a message"
      />
      <button onClick={sendMessage}>Send</button>
    </div>
  );
};

export default Chat;
