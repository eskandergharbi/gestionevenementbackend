package com.example.service_participant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.service_participant.dto.EventCountDTO;
import com.example.service_participant.model.Event;
import com.example.service_participant.model.Registration;
import com.example.service_participant.service.RegistrationService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin(origins = {"http://localhost:5300","http://localhost:3001"})
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ResponseEntity<Registration>> registerUser(@RequestParam String eventId, @RequestParam String userEmail) {
        return CompletableFuture.supplyAsync(() -> {
            Registration registration = registrationService.registerUser(eventId, userEmail);
            return ResponseEntity.ok(registration);
        });
    }

    @PostMapping("/cancel")
    public CompletableFuture<ResponseEntity<String>> cancelRegistration(@RequestParam String eventId, @RequestParam String userEmail) {
        return CompletableFuture.supplyAsync(() -> {
            registrationService.cancelRegistration(eventId, userEmail);
            return ResponseEntity.ok("Inscription annulée avec succès.");
        });
    }
    @GetMapping("/participants")
    public List<EventCountDTO> getEventParticipants() {
        return registrationService.getEventParticipantCounts();
    }
}
