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
	
	public void sendFriendshipRequest(User userFrom, User userTo) {
		FriendRequest fr = new FriendRequest(userFrom);
		frRepository.save(fr);
		userFrom.sendFriendshipRequest(userTo, fr);
		
		System.err.println("userservice.sendFriendshipRequest");
	}
}
