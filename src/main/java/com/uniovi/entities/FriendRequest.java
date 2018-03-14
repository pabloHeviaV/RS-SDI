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
	private User from;

	FriendRequest() {

	}

	public FriendRequest(User from) {
		super();
		this.from = from;
	}

	public long getId() {
		return id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	@Override
	public String toString() {
		return "FriendRequest [ from=" + from + "]";
	}
	
	

}
