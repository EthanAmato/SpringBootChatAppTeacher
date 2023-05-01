package com.example.websocketchatappteacher.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.websocketchatappteacher.models.Message;

@Controller
public class MessageController {

	// Spring annotation that maps
	// incoming messages to the /chat destination to this method.
	@MessageMapping("/chat")

//	Spring annotation that specifies the destination to which the return value of this
//	method will be sent. In this case, the return value will be sent to all subscribers 
//	of the /topic/messages destination.
	//The react useEffect is set up so that the websocket client is constantly waiting /
	//'subscribed' to this endpoint so any time there is an update to the client on /topic/messages
	//we update our react state accordingly
	@SendTo("/topic/messages")
	public String handleMessage(Message message) {
		return message.getUser() + ": " + message.getMessage();
	}
}