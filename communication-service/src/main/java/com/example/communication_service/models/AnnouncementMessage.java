package com.example.communication_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementMessage {
    private String title;
    private String content;
    private String action; // POSTED or DELETED

    // Getters and Setters
}
