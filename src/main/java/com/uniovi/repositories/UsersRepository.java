package com.uniovi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	Page<User> findAll(Pageable pageable);

	@Query("SELECT u FROM User u WHERE (LOWER(u.email) LIKE LOWER(?1) OR LOWER(u.name) LIKE LOWER(?1))")
	Page<User> searchByEmailAndName(Pageable pageable, String seachtext);

	@Query("select f from User u join u.friends f where u.id = ?1")
	Page<User> findAllFriendsByUser(Pageable pageable, Long reciever_id);

}