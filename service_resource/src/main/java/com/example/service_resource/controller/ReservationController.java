package com.example.service_resource.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import com.example.service_resource.model.Reservation;
import com.example.service_resource.repository.ReservationRepository;

import java.util.List;
@CrossOrigin(origins = 
		"http://localhost:2000")
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @GetMapping
    @Cacheable("reservations")
    public Page<Reservation> getAllReservations(@RequestParam int page, @RequestParam int size) {
        return reservationRepository.findAll(PageRequest.of(page, size));
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);
    }
    @GetMapping("/{id}")
    public void getReservation(@PathVariable Long id) {
    	reservationRepository.findById(id);
    }
}
