package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
	
	@Query("SELECT r FROM Friendship r WHERE r.reciever = ?1 ORDER BY r.id ASC")
	Page<Friendship> findAllFriendsByUser(Pageable pageable, User user);

}
