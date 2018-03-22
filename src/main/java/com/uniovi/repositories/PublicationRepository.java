package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Publication;

public interface PublicationRepository extends CrudRepository<Publication, Long>{

	@Query("select p from User u join u.posts p where u.id = ?1")
	List<Publication> findAllPublicationsByUser(long id);

}
