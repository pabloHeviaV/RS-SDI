package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;

	@Autowired
	private FriendRequestService frService;
	
	@PostConstruct
	public void init() {
		User user1 = new User("1@uniovi.es", "Pedro", "Díaz");
		user1.setPassword("123456");
		User user2 = new User("2@uniovi.es", "Lucas", "Núñez");
		user2.setPassword("123456");
		User user3 = new User("3@uniovi.es", "María", "Rodríguez");
		user3.setPassword("123456");
		User user4 = new User("4@uniovi.es", "Marta", "Almonte");
		user4.setPassword("123456");
		User user5 = new User("5@uniovi.es", "Pelayo", "Valdes");
		user5.setPassword("123456");
		User user6 = new User("6@uniovi.es", "Edward", "Núñez");
		user6.setPassword("123456");
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		
		frService.sendFriendshipRequest(user2, user1);
		frService.sendFriendshipRequest(user3, user1);
		frService.sendFriendshipRequest(user4, user1);
	}
}