package com.example.service_resource.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import com.example.service_resource.model.Salle;
import com.example.service_resource.repository.SalleRepository;

import java.util.List;
@CrossOrigin(origins = 
		"http://localhost:2000")
@RestController
@RequestMapping("/Salles")
public class SalleController {

    @Autowired
    private SalleRepository salleRepository;

    @PostMapping
    public Salle createSalle(@RequestBody Salle Salle) {
        return salleRepository.save(Salle);
    }

    @GetMapping
    @Cacheable("Salles")
    public Page<Salle> getAllSalles(@RequestParam int page, @RequestParam int size) {
        return salleRepository.findAll(PageRequest.of(page, size));
    }

    @DeleteMapping("/{id}")
    public void deleteSalle(@PathVariable Long id) {
        salleRepository.deleteById(id);
    }
    @GetMapping("/{id}")
    public void getSalle(@PathVariable Long id) {
        salleRepository.findById(id);
    }
}
