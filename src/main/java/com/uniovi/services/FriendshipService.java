package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendshipRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class FriendshipService {
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	
	public void acceptFriendRequest(User sender, User reciever) {
		Friendship fs = new Friendship(sender, reciever);
		friendshipRepository.save(fs);
		sender.acceptFriendRequest(sender, reciever, fs);
	}
	
	public Page<Friendship> getFriendsForUser(Pageable pageable, User user) {
		Page<Friendship> friends = friendshipRepository.findAllFriendsByUser(pageable, user);
		return friends;
	}

}
