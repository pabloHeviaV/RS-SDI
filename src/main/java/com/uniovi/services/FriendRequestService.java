package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendRequestRepository;

@Service
public class FriendRequestService {

	@Autowired
	private FriendRequestRepository friendRequestRepository;

	public void deleteFriendRequest(User sender,User reciever, Long id) {
		FriendRequest fr = friendRequestRepository.findOne(id);
		sender.removeFriendRequest(sender, reciever, fr);
		friendRequestRepository.delete(id);
	}

	public void sendFriendshipRequest(User sender, User reciever) {
		FriendRequest fr = new FriendRequest(sender, reciever);
		friendRequestRepository.save(fr);
		sender.sendFriendRequest(sender, reciever, fr);
	}

	public Page<FriendRequest> getFriendRequestsForUser(Pageable pageable, User user) {
		Page<FriendRequest> friendRequests = friendRequestRepository.findAllByUser(pageable, user);
		return friendRequests;
	}
}
