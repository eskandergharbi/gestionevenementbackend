package com.example.service_resource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.service_resource.model.Equipement;

public interface EquipementRepository extends JpaRepository<Equipement, Long> {
    @Query("SELECT e FROM Equipement e WHERE e.salle.id = :salleId")
    List<Equipement> getEquipementBySalleId(Long salleId);
}
