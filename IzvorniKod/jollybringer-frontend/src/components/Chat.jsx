import React, { useState, useEffect } from "react";
import { createWebSocket } from "./websocket";
import '../styles/Chat.css';

const Chat = ({ user }) => {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [socket, setSocket] = useState(null);

  // Funkcija za dohvaćanje zadnjih 7 poruka
  const fetchLast7Messages = async () => {
    try {
      const response = await fetch("http://localhost:8080/poruke/last7", { withCredentials: true });
      const data = await response.json();
      setMessages(data); // Postavi zadnjih 7 poruka
    } catch (error) {
      console.error("Error fetching last 7 messages:", error);
    }
  };

  useEffect(() => {
    // Dohvati zadnjih 7 poruka kada se komponenta učita
    fetchLast7Messages();

    const wsUrl = "ws://localhost:8080/chat"; // WebSocket URL
    const ws = createWebSocket(wsUrl, (message) => {
      setMessages((prevMessages) => [...prevMessages, message]); // Dodaj novu poruku
    });

    setSocket(ws);

    return () => {
      ws.close(); // Zatvori WebSocket kada komponenta bude demontirana
    };
  }, []);

  const sendMessage = () => {
    if (socket && socket.readyState === WebSocket.OPEN) {
      const message = {
        participant: {
          id: user.id,  // Ovo je userId
        },
        content: newMessage,
        timestamp: new Date().toISOString(),
      };
      socket.send(JSON.stringify(message)); // Pošaljite poruku
      setNewMessage(""); // Očisti input
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
            <strong className="sender">{msg.username}:</strong> 
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
