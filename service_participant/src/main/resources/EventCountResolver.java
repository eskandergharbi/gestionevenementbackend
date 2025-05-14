package com.example.service_participant.graphql;

import com.example.service_participant.dto.EventCountDTO;
import com.example.service_participant.model.Registration;
import com.example.service_participant.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class EventCountResolver {

    @Autowired
    private RegistrationRepository registrationRepository;

    @QueryMapping
    public List<EventCountDTO> getEventCounts() {
        List<Registration> registrations = registrationRepository.findAll();

        Map<String, Long> countMap = registrations.stream()
                .filter(r -> r.getEvent() != null)
                .collect(Collectors.groupingBy(r -> r.getEvent().getId(), Collectors.counting()));

        return countMap.entrySet().stream()
                .map(entry -> new EventCountDTO(entry.getKey(), entry.getValue().intValue(), 0)) // maxParticipants mis à 0
                .toList();
    }
}
