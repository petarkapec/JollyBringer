import React, { useState, useEffect, useRef } from "react";
import { createWebSocket } from "./websocket";
import "../styles/Chat.css";

const Chat = ({ user, selectedGroup }) => {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [socket, setSocket] = useState(null);

  // Ref za automatsko skrolovanje
  const messagesEndRef = useRef(null);

  // Funkcija za automatsko skrolovanje
  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  // Funkcija za dohvaćanje zadnjih 7 poruka
  const fetchLast7Messages = async () => {
    if (!selectedGroup?.id) {
      console.warn("Group ID is not available");
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/poruke/${selectedGroup?.id}`, {
        withCredentials: true,
      });
      const data = await response.json();
      setMessages(data); // Postavi zadnjih 7 poruka
    } catch (error) {
      console.error("Error fetching last 7 messages:", error);
    }
  };

  useEffect(() => {
    // Dohvati zadnjih 7 poruka kada se komponenta učita ili promeni grupa
    if (selectedGroup?.id) {
      fetchLast7Messages();
    }

    const wsUrl = "ws://localhost:8080/chat"; // WebSocket URL
    const ws = createWebSocket(wsUrl, (message) => {
      // Provjerite ako je group u poruci isti kao selectedGroup?.id
      console.log(message.group, selectedGroup);
      if (String(message.group) === String(selectedGroup?.id)) {
        setMessages((prevMessages) => [...prevMessages, message]); // Dodaj novu poruku u poruke
      }
      
    });

    setSocket(ws);

    return () => {
      ws.close(); // Zatvori WebSocket kada komponenta bude demontirana
    };
  }, [selectedGroup?.id]);

  // Skroluj na dno kada se poruke promene
  useEffect(() => {
    scrollToBottom();
    console.log("grupica je:", selectedGroup?.id);
  }, [messages]);

  const sendMessage = () => {
    if (socket && socket.readyState === WebSocket.OPEN) {
      let message = {
        username: user.email,
        content: newMessage,
        timestamp: new Date().toISOString()
      };
      socket.send(JSON.stringify(message)); // Pošaljite poruku
      message.username = user.username; // Promeni username za prikaz
      
      setNewMessage(""); // Očisti input
    } else {
      console.warn("WebSocket is not connected");
    }
  };

  return (
    <div className="chat-container w-full">
      <h2 className="chat-header">Chat</h2>
      <div className="messages-container">
        {messages
          .slice()
          .sort((a, b) => new Date(a.timestamp) - new Date(b.timestamp)) // Sortiraj po vremenu
          .map((msg, index) => (
            <div key={index} className="message">
              <strong className="sender mr-2">{msg.username}:</strong>
              <span className={"mr-2"}>{msg.content}</span>
              <em className="timestamp">({new Date(msg.timestamp).toLocaleString()})</em>
            </div>
          ))}

        {/* Ref za kraj liste poruka */}
        <div ref={messagesEndRef} />
      </div>
      <div className="input-container">
        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Type a message"
          className="input-message"
        />
        <button onClick={sendMessage} className="send-button">
          Send
        </button>
      </div>
    </div>
  );
};

export default Chat;
