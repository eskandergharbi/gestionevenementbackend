package com.example.api_gateway;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {


    @GetMapping
    public String getEvents() {
    	return "sssssssss";
    }
    }
   