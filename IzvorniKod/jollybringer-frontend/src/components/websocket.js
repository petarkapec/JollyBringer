

export const createWebSocket = (url, onMessage) => {
  const socket = new WebSocket(url);

  // Event: Connection opened
  socket.onopen = () => {
    console.log("WebSocket connected");
  };

  // Event: Message received
  socket.onmessage = (event) => {
    const data = JSON.parse(event.data);
    console.log("Message received:", data);
    if (onMessage) {
      onMessage(data);
    }
  };

  // Event: Connection closed
  socket.onclose = () => {
    console.log("WebSocket disconnected");
  };

  // Event: Error occurred
  socket.onerror = (error) => {
    console.error("WebSocket error:", error);
  };

  return socket;
};
