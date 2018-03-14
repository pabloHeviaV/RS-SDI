package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uniovi.entities.User;
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
		return "redirect:home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping("/user/list" )
	public String getListado(Model model, Pageable pageable){
		Page<User> users = usersService.getUsers(pageable); 
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "user/list";
	}
	
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
	    return "home";
	}
}
