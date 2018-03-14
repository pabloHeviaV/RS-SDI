package com.uniovi.services;

import java.util.*;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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
}