package com.example.service_participant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.service_participant.model.Event;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

}
