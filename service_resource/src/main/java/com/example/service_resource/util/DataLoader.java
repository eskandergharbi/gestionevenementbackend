package com.example.service_resource.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.service_resource.model.Equipement;
import com.example.service_resource.model.Reservation;
import com.example.service_resource.model.Salle;
import com.example.service_resource.model.SalleStatus;
import com.example.service_resource.repository.EquipementRepository;
import com.example.service_resource.repository.ReservationRepository;
import com.example.service_resource.repository.SalleRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final EquipementRepository equipementRepository;
    private final ReservationRepository reservationRepository;
    private final SalleRepository salleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create a room
        Salle salleA = new Salle(null, "Salle A", 20, SalleStatus.NorReserved, new ArrayList<>());
        salleRepository.save(salleA);
        log.info("Created Salle: {}", salleA.getNom());

        // Create a reservation
        Reservation reservation = new Reservation(null, "Mariage", LocalDateTime.now(), LocalDateTime.now().plusHours(2), salleA, new ArrayList<>());
        reservationRepository.save(reservation);

        // Add equipment associated with the room and reservation
        Equipement projecteur = new Equipement(null,"Projecteur", 50, salleA, reservation);
        Equipement table = new Equipement(null,"Table de conférence", 10, salleA, reservation);
        equipementRepository.saveAll(List.of(projecteur, table));
        log.info("Saved Equipements: Projecteur, Table");

//        // Add inventory stock
//        Inventory stockProjecteur = new Inventory(null, projecteur, 10);
//        Inventory stockTable = new Inventory(null, table, 5);
//        inventoryRepository.saveAll(List.of(stockProjecteur, stockTable));
        log.info("Inventory created for Equipements");
    }
}
