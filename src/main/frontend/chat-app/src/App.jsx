import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import React, { useState, useEffect, useRef } from 'react';

const App = () => {
  // State for messages and input value
  const [messages, setMessages] = useState([]);
  const [inputValue, setInputValue] = useState('');
  const [user, setUser] = useState('');

  // Reference to the socket connection
  const socketRef = useRef();

  // Establish socket connection and set up listeners
  useEffect(() => {
    // create a new SockJS instance and connect to the WebSocket endpoint
    const socket = new SockJS('http://localhost:8080/chat');

    // create a new Stomp client instance
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
    });

    //Subscribe to the '/topic/messages' destination when the connection is established
    //Whenever server sends us updated data, we update state accordingly
    client.onConnect = () => {
      client.subscribe('/topic/messages', (msg) => {
        console.log(msg)
        setMessages((prevMessages) => [...prevMessages, msg.body]);
      });
    };

    //Handle STOMP errors
    client.onStompError = (frame) => {
      console.log('STOMP error:', frame);
    };

    //activate client
    client.activate();

    //Store the client instance in a useRef hook for future reference
    //(Inside the handleSubmit function)
    //Takes advantage of the fact that useRef preserves state of a piece of data/object
    //between renders - even if this page rerenders, there's no need to recreate our client instance
    socketRef.current = client;


    //cleanup function called when we unmount the App component - don't want 
    //websocket server left up and running when we're not using it
    return () => {
      client.deactivate();
    };
  }, []);

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    if (inputValue.trim() && user.trim()) {
      socketRef.current.publish({
        destination: '/app/chat',
        body: JSON.stringify({ message: inputValue, user: user }),
      });
      setInputValue('');
    }
  };


  return (
    <div className="App">
      <h1>Chat App</h1>
      <ul>
        {messages.map((msg, index) => (
          <li key={index}>{msg}</li>
        ))}
      </ul>
      <form style={{ display: 'flex', flexDirection: 'column' }} onSubmit={handleSubmit}>
        <input type="text" value={user} onChange={(e) => setUser(e.target.value)} placeholder='Username' />
        <input type="text" value={inputValue} onChange={(e) => setInputValue(e.target.value)} placeholder='Message' />
        <button type="submit">Send</button>
      </form>
    </div>
  );
};

export default App;