package com.example.communication_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.communication_service.models.Announcement;
import com.example.communication_service.models.AnnouncementMessage;
import com.example.communication_service.models.AnnouncementPublisher;
import com.example.communication_service.repository.AnnouncementRepository;

import jakarta.persistence.EntityNotFoundException;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementRepository repo;

    @Autowired
    private AnnouncementPublisher publisher;

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Announcement announcement) {
        repo.save(announcement);
        publisher.publish(new AnnouncementMessage(
                announcement.getTitle(),
                announcement.getContent(),
                "POSTED"
        ));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
    	Announcement ann = repo.findById(id)
    	        .orElseThrow(() -> new EntityNotFoundException("Announcement with id " + id + " not found"));
        repo.delete(ann);
        publisher.publish(new AnnouncementMessage(
                ann.getTitle(),
                ann.getContent(),
                "DELETED"
        ));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Announcement> all() {
        return repo.findAll();
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Announcement updatedAnnouncement) {
        Announcement existing = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Announcement with id " + id + " not found"));
        
        existing.setTitle(updatedAnnouncement.getTitle());
        existing.setContent(updatedAnnouncement.getContent());
        repo.save(existing);

        publisher.publish(new AnnouncementMessage(
                updatedAnnouncement.getTitle(),
                updatedAnnouncement.getContent(),
                "UPDATED"
        ));
        
        return ResponseEntity.ok().build();
    }

}
