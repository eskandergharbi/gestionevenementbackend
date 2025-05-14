
package com.example.service_evenement.util;

import com.example.service_evenement.model.Event;
import com.example.service_evenement.model.EventStatus;
import com.example.service_evenement.repository.EventRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final EventRepository eventRepository;

    @Override
    public void run(String... args) throws Exception {
        if (eventRepository.count() < 1) {
            Event event = new Event();
            event.setName("Concert de Rock");
            event.setEventStatus(EventStatus.OUVERT); // Par exemple
            event.setCategory("Musique");
            event.setParticipantCount(0);
            event.setMaxParticipants(100);

            eventRepository.save(event);
        }
    }
}

