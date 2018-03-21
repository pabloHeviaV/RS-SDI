package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Publication;

public interface PublicationRepository extends CrudRepository<Publication, Long>{

}
