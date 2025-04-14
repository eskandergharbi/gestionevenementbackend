package com.example.service_participant.service;

import com.example.service_participant.dto.EventCountDTO;
import com.example.service_participant.model.*;
import com.example.service_participant.repository.EventRepository;
import com.example.service_participant.repository.RegistrationRepository;
import com.example.service_participant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${event.service.url}")
    private String eventServiceUrl;

    @Autowired
    private NotificationProducer notificationProducer;

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    public Registration registerUser(String eventId, String userEmail) {
        try {
            Event event = getEventById(eventId);
            if (event == null) {
                throw new RuntimeException("Événement introuvable");
            }

            User user = userRepository.findByEmail(userEmail);
            if (user == null) {
                user = new User();
                user.setEmail(userEmail);
                user = userRepository.save(user);
            }

            Optional<Registration> existingRegistration = registrationRepository.findByUserAndEvent(user, event);
            if (existingRegistration.isPresent()) {
                throw new RuntimeException("Utilisateur déjà inscrit à cet événement.");
            }

            Registration registration = new Registration();
            registration.setEvent(event);
            registration.setUser(user);
            registration.setRegistrationDate(LocalDate.now());
            registration.setStatus(RegistrationStatus.INSCRIT);
            registration = registrationRepository.save(registration);

            // Envoyer une notification via Kafka
            Notification notification = new Notification(userEmail, "Votre inscription à l'événement " + event.getName() + " a été confirmée.");
            notificationProducer.sendNotification(notification);

            return registration;
        } catch (RuntimeException ex) {
            throw ex;
        }
    }

    public void cancelRegistration(String eventId, String userEmail) {
        try {
            Event event = getEventById(eventId);
            if (event == null) {
                throw new RuntimeException("Événement introuvable");
            }

            User user = userRepository.findByEmail(userEmail);
            if (user == null) {
                throw new RuntimeException("Utilisateur introuvable");
            }

            Optional<Registration> registrationOpt = registrationRepository.findByUserAndEvent(user, event);
            if (registrationOpt.isEmpty()) {
                throw new RuntimeException("Aucune inscription trouvée pour cet utilisateur et cet événement.");
            }

            Registration registration = registrationOpt.get();
            registration.setStatus(RegistrationStatus.ANNULE);
            registrationRepository.save(registration);

            // Envoyer une notification via Kafka
            Notification notification = new Notification(userEmail, "Votre inscription à l'événement " + event.getName() + " a été annulée.");
            notificationProducer.sendNotification(notification);
        } catch (RuntimeException ex) {
            throw ex;
        }
    }

    private Event getEventById(String eventId) {
        try {
            ResponseEntity<Event> response = restTemplate.getForEntity(eventServiceUrl + "/" + eventId, Event.class);
            if (response.getBody() == null) {
                throw new RuntimeException("No event found for ID: " + eventId);
            }
            return response.getBody();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to retrieve event with ID: " + eventId, ex);
        }
    }


    public List<EventCountDTO> getEventParticipantCounts() {
        Aggregation aggregation = Aggregation.newAggregation(
            // Step 1: Join with the "events" collection
            Aggregation.lookup("events", "event", "_id", "eventDetails"),

            // Step 2: Flatten the eventDetails array
            Aggregation.unwind("eventDetails", true), // true = preserve null/empty arrays

            // Step 3: Group by event ID
            Aggregation.group("event.$id")
                .count().as("participantCount") // Count registrations
                .first("eventDetails.maxParticipants").as("maxParticipants"), // Get maxParticipants

            // Step 4: Restructure output to match EventCountDTO
            Aggregation.project()
                .andExpression("toString(_id)").as("eventId")
                .and("participantCount").as("participantCount")
                .and("maxParticipants").as("maxParticipants")
        );

        AggregationResults<EventCountDTO> results = mongoTemplate.aggregate(
            aggregation,
            "registrations", // The collection to aggregate on
            EventCountDTO.class // Result mapped to this class
        );

        return results.getMappedResults();
    }



}
