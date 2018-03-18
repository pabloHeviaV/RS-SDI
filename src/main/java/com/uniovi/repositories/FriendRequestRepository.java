package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long>{

	@Query("SELECT r FROM FriendRequest r WHERE r.reciever = ?1 ORDER BY r.id ASC ")
	Page<FriendRequest> findAllByUser(Pageable pageable, User user);

}
