package com.uniovi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Publication {

	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String body;
	private Date creationDate;
	
	@ManyToOne
	private User owner;
	
	public Publication(){
		this.creationDate= new Date();
	}

	public Publication(String title, String body) {
		super();
		this.title = title;
		this.body = body;
		this.creationDate= new Date();
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getId() {
		return id;
	}
	
	
}
