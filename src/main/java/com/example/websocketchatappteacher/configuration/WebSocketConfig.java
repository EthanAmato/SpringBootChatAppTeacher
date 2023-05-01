package com.example.websocketchatappteacher.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
//Broker-backed messaging: A message broker is an intermediary component in a messaging system that helps manage and route messages between 
//different applications or services. It decouples the sender and receiver applications, 
//allowing them to evolve independently. In the context of WebSockets, a message broker manages and routes the messages sent between connected clients.

//Higher-level messaging sub-protocol: A higher-level messaging sub-protocol is built on top of the WebSocket protocol and provides additional features for message handling.
//For our app, we will be using the STOMP (Simple Text Oriented Messaging Protocol) which defines the format, structure, and processing semantics for easy communication
//between our client and the messenger
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) { // responsible for configuring the message broker
		config.enableSimpleBroker("/topic"); // destination prefix of broker set to the /topic endpoint
												// all messages with a destination strating with /topic will be handled
												// by this message broker
		config.setApplicationDestinationPrefixes("/app"); // all messages sent by clients that start with /app will be
															// routed to the message handling methods in the controllers
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
	    registry.addEndpoint("/chat")
	            .setAllowedOriginPatterns("*")
	            .withSockJS()
	            .setClientLibraryUrl("//cdn.jsdelivr.net/sockjs/latest/sockjs.min.js"); //By setting the setClientLibraryUrl() method, you ensure that the client uses the correct version of the SockJS library when connecting to the server.
	}


	// Addendpoint: registers new STOMP endpoint with path /chat
	// clients will use this endpoint to connect to the websocket server

	// allowedOrigins: allows all origins to connect to our /chat endpoint - this is
	// a brute force measure for our test app so if you were to build a production
	// grade
	// app, you would restrict this to only trusted origins so as to only let
	// certain websites call your websocket server

	// withSockJS: enables SockJS fallback options. SockJS is a JS library that
	// allows for similar functionality as websockets and serves as a failsafe for
	// people connecting
	// from browsers that don't support WebSocket connections
}
