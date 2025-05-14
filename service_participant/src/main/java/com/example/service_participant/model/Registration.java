package com.example.service_participant.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "registrations") // Nom de la collection dans MongoDB
public class Registration {

    @Id
    private String id; // ID de type String pour MongoDB

    @DBRef
    private Event event; // Utilisation de @DBRef pour les relations

    @DBRef
    private User user; // Même chose pour User

    private LocalDate registrationDate;
    private RegistrationStatus status;
}
