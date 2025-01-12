import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

const socketURL = "http://localhost:8080/chat"; // Spring Boot WebSocket endpoint

export const connectToWebSocket = (onMessageReceive) => {
  const client = new Client({
    brokerURL: null, // Required for browsers that support WebSocket
    webSocketFactory: () => new SockJS(socketURL), // Fallback for SockJS
    onConnect: () => {
      console.log("Connected!");

      // Subscribe to messages
      client.subscribe("/topic/messages", (response) => {
        const message = JSON.parse(response.body);
        onMessageReceive(message);
      });
    },
    onDisconnect: () => console.log("Disconnected!"),
  });

  client.activate(); // Start connection
  return client;
};