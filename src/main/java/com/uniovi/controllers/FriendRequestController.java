package com.uniovi.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	private static final Logger LOG = LoggerFactory.getLogger(FriendRequestController.class);
	
	@RequestMapping("/friendRequest/list")
	public String getList(Model model, Pageable pageable){
		User user = usersService.getCurrentUser();
		Page<FriendRequest> friendRequests = friendRequestService.getFriendRequestsForUser(pageable, user);
		model.addAttribute("friendRequestList", friendRequests.getContent());
		model.addAttribute("page", friendRequests);
		return "friendRequest/list";
	}
	
	@RequestMapping("/friendRequest/list/update")
	public String updateList(Model model, Pageable pageable) {
		User user = usersService.getCurrentUser();
		Page<FriendRequest> friendRequests = friendRequestService.getFriendRequestsForUser(pageable, user);
		model.addAttribute("friendRequestList", friendRequests.getContent());
		model.addAttribute("page", friendRequests);
		return "friendRequest/list :: tableFriendRequest";
	}
	
	
	@RequestMapping("user/acceptFR/{idFr}/{idSender}")
	public String acceptFriendRequest(Model model, @PathVariable Long idFr, @PathVariable Long idSender) {
		User reciever = usersService.getCurrentUser();
		User sender = usersService.getUser(idSender);
		usersService.acceptFriendRequest(sender, reciever);
		friendRequestService.deleteFriendRequest(sender, reciever, idFr);
		LOG.info("Aceptada la petición de amistad del usuario: " + sender.getId()
				+ " para el usuario: " + reciever.getId());
		
		return "redirect:/friendRequest/list/update";
	}

}
