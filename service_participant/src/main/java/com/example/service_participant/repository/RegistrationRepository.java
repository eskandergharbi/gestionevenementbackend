package com.example.service_participant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.service_participant.model.Event;
import com.example.service_participant.model.Registration;
import com.example.service_participant.model.User;
@Repository
public interface RegistrationRepository extends MongoRepository<Registration, String> {

    List<Registration> findByEvent(Event event);


	Optional<Registration> findByUserAndEvent(User user, Event event);


}
