package com.example.service_evenement.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.service_evenement.model.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    Page<Event> findByCategory(String category, Pageable pageable);

}
