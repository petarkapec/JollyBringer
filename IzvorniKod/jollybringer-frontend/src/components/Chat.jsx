
import '../styles/Chat.css'
import React, { useState, useEffect } from "react";
import { connectToWebSocket } from "./connectToWebSocket";

const Chat = () => {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [client, setClient] = useState(null);
  const { role, user, loading } = useAuth();

  // Start WebSocket connection
  useEffect(() => {
    const wsClient = connectToWebSocket((message) => {
      setMessages((prevMessages) => [...prevMessages, message]); // Add received message
    });

    setClient(wsClient);

    return () => {
      wsClient.deactivate(); // Cleanup connection on component unmount
    };
  }, []);

  // Send message
  const sendMessage = () => {
    if (client && client.connected) {
      client.publish({
        destination: "/app/sendMessage",
        body: JSON.stringify({ sender: user.email, content: newMessage, timestamp: new Date().toISOString() }),
      });
      setNewMessage(""); // Clear input
    }
  };

  return (
    <div>
      <div>
        <h2>Chatroom</h2>
        <div style={{ border: "1px solid #ccc", height: "300px", overflowY: "scroll" }}>
          {messages.map((msg, index) => (
            <div key={index}>
              <strong>{msg.sender}:</strong> {msg.content} <em>({msg.timestamp})</em>
            </div>
          ))}
        </div>
      </div>
      <div>
        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Type a message"
        />
        <button onClick={sendMessage}>Send</button>
      </div>
    </div>
  );
};

export default Chat;