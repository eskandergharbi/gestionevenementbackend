package com.example.service_resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.service_resource.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
