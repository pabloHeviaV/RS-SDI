package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendRequestRepository;

@Service
public class FriendRequestService {

	@Autowired
	private FriendRequestRepository frRepository;

	public void deleteFriendRequest(Long id) {
		frRepository.delete(id);
	}

	public void sendFriendshipRequest(User sender, User reciever) {
		FriendRequest fr = new FriendRequest(sender, reciever);
		frRepository.save(fr);
		sender.sendFriendshipRequest(sender, reciever, fr);
	}
}
