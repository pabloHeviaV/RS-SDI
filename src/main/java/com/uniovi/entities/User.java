package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set; //A collection that contains no duplicate elements

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String email;
	private String name;
	private String lastName;
	private String role;

	private String password;
	@Transient // propiedad que no se almacena e la tabla.
	private String passwordConfirm;

	@OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
	private Set<FriendRequest> friendRequests = new HashSet<FriendRequest>();
	
	@Transient
	private Set<User> friends = new HashSet<User>();

	public User(String email, String name, String lastName) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public Set<FriendRequest> getFriendRequests() {
		return friendRequests;
	}

	public void setFriendRequests(Set<FriendRequest> friendRequests) {
		this.friendRequests = friendRequests;
	}
	
	
	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	/**
	 * Comprueba si el usuario actual es amigo del usuario pasado por parámetro.
	 * 
	 * @param user
	 * @return true si son amigos o es él mismo, false si no lo son
	 */
	public boolean checkFriendship(User user) {
		if (this.equals(user) || friends.contains(user))
			return true;
		return false;
	}

	/**
	 * Añade una petición de amistad de el usuario activo a la lista de peticiones
	 * del user pasado por parámetro.
	 * 
	 * @param user
	 */
	public void sendFriendshipRequest(User userTo, FriendRequest fr) {
		userTo.getFriendRequests().add(fr);
		System.err.println("user.sendFriendshipRequest");
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", lastName=" + lastName + ", friendRequests="
				+ friendRequests + ", friends=" + friends + "]";
	}
	
	
}