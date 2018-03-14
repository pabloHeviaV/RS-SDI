package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FriendRequest {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User sender;
	
	@ManyToOne
	private User reciever;

	FriendRequest() {

	}

	public FriendRequest(User sender, User reciever) {
		super();
		this.sender = sender;
		this.reciever = reciever;
	}

	public long getId() {
		return id;
	}


	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReciever() {
		return reciever;
	}

	public void setReciever(User reciever) {
		this.reciever = reciever;
	}

	@Override
	public String toString() {
		return "FriendRequest [sender=" + sender + ", reciever=" + reciever + "]";
	}	

}
