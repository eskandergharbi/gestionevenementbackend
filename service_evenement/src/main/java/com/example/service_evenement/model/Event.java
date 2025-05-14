package com.example.service_evenement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "events") // Nom de la collection MongoDB
public class Event implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    private String id; // Utilisation d'un String pour l'ObjectId de MongoDB
    private String name;
    private EventStatus eventStatus;
    private String category;
    private int participantCount;
    private int maxParticipants;
}
