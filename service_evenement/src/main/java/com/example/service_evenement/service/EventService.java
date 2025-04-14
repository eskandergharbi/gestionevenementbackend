package com.example.service_evenement.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.service_evenement.model.Event;
import com.example.service_evenement.model.EventStatus;
import com.example.service_evenement.repository.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Cacheable("events")
    public Page<Event> getEvents(String category, Pageable pageable) {
        if (category == null) {
            return eventRepository.findAll(pageable);
        } else {
            return eventRepository.findByCategory(category, pageable);
        }
    }

    @CacheEvict(value = "events", allEntries = true)
    public Event createEvent(Event event) {
        updateRegistrationStatus(event);
        return eventRepository.save(event);
    }
    @Cacheable(value = "events", key = "#id")
    public Optional<Event> getEventById(String id) {
        return eventRepository.findById(id);
    }
    @CachePut(value = "events", key = "#id")
    public Event updateEvent(String id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setName(eventDetails.getName());
        event.setCategory(eventDetails.getCategory());
        event.setParticipantCount(eventDetails.getParticipantCount());
        event.setMaxParticipants(eventDetails.getMaxParticipants());
        updateRegistrationStatus(event);
        return eventRepository.save(event);
    }

    @CacheEvict(value = "events", allEntries = true)
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }

    private void updateRegistrationStatus(Event event) {
        if (event.getParticipantCount() >= event.getMaxParticipants()) {
            event.setEventStatus(EventStatus.COMPLET);
        } else if (event.getParticipantCount() == 0) {
            event.setEventStatus(EventStatus.OUVERT);
        } else {
            event.setEventStatus(EventStatus.FERME);
        }
    }
    public void updateParticipantCount(String eventId, int count) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setParticipantCount(count);
        eventRepository.save(event);
    }
    

}
