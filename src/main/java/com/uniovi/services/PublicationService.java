package com.uniovi.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.repositories.PublicationRepository;

@Service
public class PublicationService {

	@Autowired
	private PublicationRepository publicationRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(PublicationService.class);
	
	public void addPublication(Publication publication, User owner) {
		publication.setOwner(owner);
		publicationRepository.save(publication);
		LOG.info("Publicación con id " + publication.getId().toString() + " ha sido creada.");
	}

	public List<Publication> getPublicationsForUser(long id) {
		return publicationRepository.findAllPublicationsByUser(id);
	}
}
