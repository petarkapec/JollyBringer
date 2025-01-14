import React, { useState, useEffect, useRef } from "react";
import { createWebSocket } from "./websocket";
import '../styles/Chat.css';

const Chat = ({ user }) => {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [socket, setSocket] = useState(null);

  const backendUrl = import.meta.env.VITE_BACKEND_URL;
  const wsVURL = import.meta.env.VITE_BACKEND_WS;

  // Ref za automatsko skrolovanje
  const messagesEndRef = useRef(null);

  // Funkcija za automatsko skrolovanje
  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  // Funkcija za dohvaćanje zadnjih 7 poruka
  const fetchLast7Messages = async () => {
    try {
      const response = await fetch(`${backendUrl}/poruke/last7`, { withCredentials: true });
      const data = await response.json();
      setMessages(data); // Postavi zadnjih 7 poruka
    } catch (error) {
      console.error("Error fetching last 7 messages:", error);
    }
  };

  useEffect(() => {
    // Dohvati zadnjih 7 poruka kada se komponenta učita
    fetchLast7Messages();

    const wsUrl = `${wsVURL}/chat`; // WebSocket URL
    const ws = createWebSocket(wsUrl, (message) => {
      // setMessages((prevMessages) => [...prevMessages, message]); // Dodaj novu poruku
    });

    setSocket(ws);

    return () => {
      ws.close(); // Zatvori WebSocket kada komponenta bude demontirana
    };
  }, []);

  // Skroluj na dno kada se poruke promene
  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const sendMessage = () => {
    if (socket && socket.readyState === WebSocket.OPEN) {
      let message = {
        username: user.email,
        content: newMessage,
        timestamp: new Date().toISOString(),
      };
      socket.send(JSON.stringify(message)); // Pošaljite poruku
      message.username = user.username; // Change the username to display
      setMessages((prevMessages) => [...prevMessages, message]); // Add message to the list
      setNewMessage(""); // Očisti input
    } else {
      console.warn("WebSocket is not connected");
    }
  };

  return (
    <div className="bg-customGray rounded-lg p-4 shadow-md flex flex-col h-auto">
      <h2 className="text-xl font-semibold text-white mb-4 h-auto">Chat</h2>
      <div className="bg-customGrayLighter rounded-lg p-4 mb-4 flex-grow overflow-y-auto max-h-96">
        {messages
          .slice()
          .sort((a, b) => new Date(a.timestamp) - new Date(b.timestamp)) // Sortiramo po vremenu
          .map((msg, index) => (
            <div key={index} className="mb-2">
              <strong className="text-green-500">{msg.username}:</strong>
              <span className="text-white ml-2">{msg.content}</span>
              <em className="text-gray-400 ml-2 text-sm">({new Date(msg.timestamp).toLocaleTimeString()})</em>
            </div>
          ))}
        {/* Ref za kraj liste poruka */}
        <div ref={messagesEndRef} />
      </div>
      <div className="flex items-center">
        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Type a message"
          className="flex-grow p-2 rounded bg-gray-700 text-white mr-2"
        />
        <button
          onClick={sendMessage}
          className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700"
        >
          Send
        </button>
      </div>
    </div>
  );
};

export default Chat;
