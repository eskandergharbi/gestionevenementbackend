package com.example.service_evenement;
import com.example.service_evenement.model.Event;
import com.example.service_evenement.model.EventStatus;
import com.example.service_evenement.repository.EventRepository;
import com.example.service_evenement.service.EventService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event("1", "Event 1", EventStatus.OUVERT, "Category 1", 10, 100);
    }

    @Test
    void testCreateEvent() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event createdEvent = eventService.createEvent(event);

        assertEquals("Event 1", createdEvent.getName());
        assertEquals(EventStatus.OUVERT, createdEvent.getEventStatus());
    }

    @Test
    void testGetEventById() {
        when(eventRepository.findById("1")).thenReturn(Optional.of(event));

        Optional<Event> foundEvent = eventService.getEventById("1");

        assertTrue(foundEvent.isPresent());
        assertEquals("Event 1", foundEvent.get().getName());
    }

    @Test
    void testUpdateEvent() {
        when(eventRepository.findById("1")).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event updatedEvent = eventService.updateEvent("1", event);

        assertEquals("Event 1", updatedEvent.getName());
    }

    @Test
    void testDeleteEvent() {
        when(eventRepository.findById("1")).thenReturn(Optional.of(event));

        eventService.deleteEvent("1");

        assertEquals(0, eventRepository.findAll(PageRequest.of(0, 5)).getTotalElements());
    }

    @Test
    void testGetEvents() {
        when(eventRepository.findAll(PageRequest.of(0, 5))).thenReturn(new PageImpl<>(Collections.singletonList(event)));

        Page<Event> events = eventService.getEvents(null, PageRequest.of(0, 5));

        assertEquals(1, events.getTotalElements());
    }
}

