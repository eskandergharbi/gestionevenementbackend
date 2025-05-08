//package com.example.service_resource.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.web.bind.annotation.*;
//
//import com.example.service_resource.model.Inventory;
//import com.example.service_resource.repository.InventoryRepository;
//
//@CrossOrigin(origins = 
//		"http://localhost:2000")
//@RestController
//@RequestMapping("/inventory")
//public class InventoryController {
//
//    @Autowired
//    private InventoryRepository inventoryRepository;
//
//    @PostMapping
//    public Inventory createInventory(@RequestBody Inventory inventory) {
//        return inventoryRepository.save(inventory);
//    }
//
//    @GetMapping
//    @Cacheable("inventory")
//    public Page<Inventory> getAllInventory(@RequestParam int page, @RequestParam int size) {
//        return inventoryRepository.findAll(PageRequest.of(page, size));
//    }
//
//    @PutMapping("/{id}")
//    public Inventory updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
//        inventory.setId(id);
//        return inventoryRepository.save(inventory);
//    }
//}