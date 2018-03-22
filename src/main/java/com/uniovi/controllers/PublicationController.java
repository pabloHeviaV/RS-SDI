package com.uniovi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.services.PublicationService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddPublicationValidator;

@Controller
public class PublicationController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private PublicationService publicationService;

	@Autowired
	private AddPublicationValidator addPublicationValidator;

	@RequestMapping(value = "/publication/add", method = RequestMethod.GET)
	public String addPublication(Model model) {
		model.addAttribute("publication", new Publication());
		return "publication/add";
	}

	@RequestMapping(value = "/publication/add", method = RequestMethod.POST)
	public String addPublication(@Validated Publication publication, BindingResult result, Model model) {
		addPublicationValidator.validate(publication, result);
		if (result.hasErrors())
			return "publication/add";

		publicationService.addPublication(publication, usersService.getCurrentUser());
		return "redirect:/publication/list";
	}

	@RequestMapping(value = "/publication/list")
	public String getList(Model model) {
		User user = usersService.getCurrentUser();
		List<Publication> publications = publicationService.getPublicationsForUser(user.getId());
		model.addAttribute("publicationsList", publications);

		return "publication/list";
	}

}
