package com.uniovi.controllers;

import java.util.LinkedList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;
import com.uniovi.services.FriendRequestService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UserController {

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private FriendRequestService friendRequestService;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result, Model model) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:user/list";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping("/user/list")
	public String getList(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUsersByEmailAndName(pageable, searchText);
		} else {
			users = usersService.getUsers(pageable);
		}
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("activeUser", usersService.getCurrentUser());
		model.addAttribute("page", users);
		return "user/list";
	}

	@RequestMapping("/user/list/update")
	public String updateList(Model model, Pageable pageable) {
		Page<User> users = usersService.getUsers(pageable);
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("activeUser", usersService.getCurrentUser());
		model.addAttribute("page", users);
		return "user/list :: tableUsers";
	}

	@RequestMapping("user/sendFR/{id}")
	public String sendFriendRequest(Model model, @PathVariable Long id) {
		User sender = usersService.getCurrentUser();
		User reciever = usersService.getUser(id);

		friendRequestService.sendFriendshipRequest(sender, reciever);
		return "redirect:/user/list/update";
	}
	
	
	@RequestMapping("/user/listFriends")
	public String getList(Model model, Pageable pageable){
		User user = usersService.getCurrentUser();
		Page<User> userFriends = usersService.getFriendsForUser(pageable, user.getId());
		model.addAttribute("usersListFriend", userFriends.getContent());
		model.addAttribute("page", userFriends);
		return "user/listFriends";
	}
	
//	@RequestMapping("/user/listFriends")
//	public String getList(Model model){
//		User user = usersService.getCurrentUser();
//		Set<User> userFriends = usersService.getFriendsForUser(user.getId());
//		model.addAttribute("usersListFriend", userFriends);
//		return "user/listFriends";
//	}
	

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

}
