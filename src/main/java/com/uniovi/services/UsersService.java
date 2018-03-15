package com.uniovi.services;

import java.util.*;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	public void init() {
	}

	public Page<User> getUsers(Pageable pageable) {
		Page<User> users = usersRepository.findAll(pageable);
		return users;
	}

	public Page<User> searchUsersByEmailAndName(Pageable pageable, String searchText) {
		searchText = "%"+searchText+"%"; 
		Page<User> users = usersRepository.searchByEmailAndName(pageable,searchText);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findOne(id);
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public void deleteUser(Long id) {
		usersRepository.delete(id);
	}
	
	public void acceptFriendRequest(User sender, User reciever) {
		sender.acceptFriendRequest(sender, reciever);
	}
	
	public Page<User> getFriendsForUser(Pageable pageable, Long reciever_id) {
		Page<User> friends = usersRepository.findAllFriendsByUser(pageable, reciever_id);
		return friends;
	}
	
//	public Set<User> getFriendsForUser(Long reciever_id) {
//		User user = usersRepository.findOne(reciever_id);
//		return user.getFriends();
//	}
	
	/**
	 * Devuelve el usuario con sesión iniciada en el sistema.
	 * @return
	 */
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = getUserByEmail(email);
		return activeUser;
	}


	
	
}