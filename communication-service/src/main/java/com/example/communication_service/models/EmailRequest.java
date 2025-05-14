package com.example.communication_service.models;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String body;
    // Getters and Setters
}
