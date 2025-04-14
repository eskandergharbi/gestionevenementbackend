package com.example.service_resource.controller;

import java.util.List;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import com.example.service_resource.model.Equipement;
import com.example.service_resource.model.Reservation;
import com.example.service_resource.model.Salle;
import com.example.service_resource.repository.EquipementRepository;
import com.example.service_resource.repository.ReservationRepository;
import com.example.service_resource.repository.SalleRepository;

@CrossOrigin(origins = 
		"http://localhost:2000")
@RestController
@RequestMapping("/Equipements")
public class EquipementController {

    @Autowired
    private EquipementRepository equipementRepository;
    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    @PostMapping
    public Equipement createEquipement(@RequestBody Equipement equipement) {
        // Ensure that the Salle object is properly set
        if (equipement.getSalle() != null) {
            Salle salle = salleRepository.findById(equipement.getSalle().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Salle not found"));
            equipement.setSalle(salle);
        }

        // Ensure that the Reservation object is properly set
        if (equipement.getReservation() != null) {
            Reservation reservation = reservationRepository.findById(equipement.getReservation().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
            equipement.setReservation(reservation);
        }

        return equipementRepository.save(equipement);
    }


    @GetMapping
    @Cacheable("Equipements")
    public Page<Equipement> getAllEquipements(@RequestParam int page, @RequestParam int size) {
        return equipementRepository.findAll(PageRequest.of(page, size));
    }

    @PutMapping("/{id}")
    public Equipement updateEquipement(@PathVariable Long id, @RequestBody Equipement Equipement) {
        Equipement.setId(id);
        return equipementRepository.save(Equipement);
    }
    @GetMapping("/{id}")
    public List<Equipement> getEquipementsBySalleId(@PathVariable Long id) {
    	
		return equipementRepository.getEquipementBySalleId(id);
    }
}