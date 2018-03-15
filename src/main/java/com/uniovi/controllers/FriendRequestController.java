package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;
import com.uniovi.services.FriendRequestService;
import com.uniovi.services.UsersService;

@Controller
public class FriendRequestController {
	
	@Autowired
	private FriendRequestService friendRequestService;
	
	@Autowired 
	private UsersService usersService;
	
	@RequestMapping("/friendRequest/list")
	public String getList(Model model, Pageable pageable){
		
		
		User user = usersService.getCurrentUser();
		Page<FriendRequest> friendRequests = friendRequestService.getFriendRequestsForUser(pageable, user);
		
		model.addAttribute("friendRequestList", friendRequests.getContent());
		model.addAttribute("page", friendRequests);
		return "friendRequest/list";
	}
	
	

}
