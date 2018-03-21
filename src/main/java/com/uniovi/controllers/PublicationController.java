package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Publication;
import com.uniovi.services.PublicationService;
import com.uniovi.services.UsersService;

@Controller
public class PublicationController {

	@Autowired 
	private UsersService usersService;
	
	@Autowired 
	private PublicationService publicationService;
	
	@RequestMapping(value = "/publication/add",  method = RequestMethod.GET)
	public String addPublication(Model model) {
		model.addAttribute("publication", new Publication());
		return "publication/add";
	}
	
	@RequestMapping(value = "/publication/add",  method = RequestMethod.POST)
	public String addPublication(Publication publication, Model model) {
		publicationService.addPublication(publication, usersService.getCurrentUser());
		return "/home";
	}
	
}
