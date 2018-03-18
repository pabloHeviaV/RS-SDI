package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.UsersService;

@Controller
public class FriendshipController {
	
	@Autowired 
	private UsersService usersService;
	
	@Autowired
	private FriendshipService friendshipService;

	@RequestMapping("/friendship/list")
	public String getList(Model model, Pageable pageable){
		User user = usersService.getCurrentUser();
		Page<Friendship> friendships = friendshipService.getFriendsForUser(pageable, user);
		model.addAttribute("friendshipList", friendships.getContent());
		model.addAttribute("page", friendships);
		return "friendship/list";
	}
	
}
