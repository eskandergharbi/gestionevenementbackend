package com.example.service_resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.service_resource.model.Inventory;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
