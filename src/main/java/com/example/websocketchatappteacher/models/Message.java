package com.example.websocketchatappteacher.models;

public class Message {
	private String user;
	private String message;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Message [user=" + user + ", message=" + message + "]";
	}
	public Message(String user, String message) {
		this.user = user;
		this.message = message;
	}
	
	
}
