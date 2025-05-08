package com.example.service_evenement.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.service_evenement.model.Event;
import com.example.service_evenement.service.EventService;
@CrossOrigin(origins = {
		"http://localhost:3002",
	    "http://localhost:5300",

	})
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public Page<Event> getEvents(
            @RequestParam(required = false) String category,
            @PageableDefault(size = 5) Pageable pageable) {
        return eventService.getEvents(category, pageable);
    }
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable String id, @RequestBody Event eventDetails) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDetails);
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{eventId}")
    public ResponseEntity<Optional<Event>> getEventById(@PathVariable String eventId) {
        Optional<Event> event = eventService.getEventById(eventId);
        return event.isPresent() ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }
    @PutMapping("/{eventId}/participantCount")
    public ResponseEntity<Void> updateParticipantCount(@PathVariable String eventId, @RequestBody int count) {
        eventService.updateParticipantCount(eventId, count);
        return ResponseEntity.ok().build();
    }
    

}