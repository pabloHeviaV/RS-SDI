package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set; //A collection that contains no duplicate elements

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.*;

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
	@Transient
	private String passwordConfirm;

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private Set<FriendRequest> friendRequestsSenders = new HashSet<FriendRequest>();

	@OneToMany(mappedBy = "reciever", cascade = CascadeType.ALL)
	private Set<FriendRequest> friendRequestsRecievers = new HashSet<FriendRequest>();

	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name = "friendship", joinColumns = @JoinColumn(name = "sender_id", referencedColumnName = "id"), 
				inverseJoinColumns = @JoinColumn(name = "reciever_id", referencedColumnName = "id"))
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

	public Set<FriendRequest> getFriendRequestsSenders() {
		return friendRequestsSenders;
	}

	public void setFriendRequestsSenders(Set<FriendRequest> friendRequestsSenders) {
		this.friendRequestsSenders = friendRequestsSenders;
	}

	public Set<FriendRequest> getFriendRequestsRecievers() {
		return friendRequestsRecievers;
	}

	public void setFriendRequestsRecievers(Set<FriendRequest> friendRequestsRecievers) {
		this.friendRequestsRecievers = friendRequestsRecievers;
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
	public String checkFriendshipStatus(User user) {
		if (this.equals(user) || friends.contains(user))
			return "FRIENDS";
		if (existsRequest(user))
			return "REQUEST_SENT";
					
		return "NOT_FRIENDS";
	}

	/**
	 * Añade una peticion de amistad a la lista de peticiones enviadas del
	 * usuario que la envia y a la lista de peticiones recibidas del usuario que recibe la peticion.
	 * 
	 * @param sender
	 * @param reciever
	 * @param fr
	 */
	public void sendFriendRequest(User sender, User reciever, FriendRequest fr) {
		sender.getFriendRequestsSenders().add(fr);
		reciever.getFriendRequestsRecievers().add(fr);
	}
	
	/**
	 * Elimina una peticion de amistad de la lista de peticiones enviadas del 
	 * usuario que la envia y de la lista de peticiones recibidas del usuario que la recibe.
	 * 
	 * @param sender
	 * @param reciever
	 * @param fr
	 */
	public void removeFriendRequest(User sender, User reciever,FriendRequest fr) {
		sender.getFriendRequestsSenders().remove(fr);
		reciever.getFriendRequestsRecievers().remove(fr);
		fr.setSender(null);
		fr.setReciever(null);
	}
	
	/**
	 * Creamos una relación de amistad entre el usuario que envió
	 * la solicitud de amistad y el que la recibió añadiendoles a sus respectivas
	 * listas de amigos.
	 * 
	 * @param sender
	 * @param reciever
	 */
	public void acceptFriendRequest(User sender, User reciever) {
		sender.getFriends().add(reciever);
		reciever.getFriends().add(sender);
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", lastName=" + lastName + ", friendRequestsSenders="
				+ friendRequestsSenders + ", friendRequestsRecievers=" + friendRequestsRecievers + ", friends="
				+ friends + "]";
	}
	
	/**
	 * Comprueba si el usuario activo le ha enviado una petición de amistad al
	 * usuario pasado por parámetro.
	 * 
	 * @param reciever
	 * @return
	 */
	public boolean existsRequest(User reciever) {
		for(FriendRequest fr: getFriendRequestsSenders())
			if(fr.getReciever().equals(reciever))
				return true;
		return false;
}

}