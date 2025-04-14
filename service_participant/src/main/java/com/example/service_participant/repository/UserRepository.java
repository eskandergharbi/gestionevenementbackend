package com.example.service_participant.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.service_participant.model.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Page<User> findAll(Pageable pageable);
    User findByEmail(String email);
	Optional<User> findById(String id);

}
