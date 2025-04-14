package com.example.service_participant.dto;

//src/main/java/com/example/service_participant/dto/EventCountDTO.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventCountDTO {
 private String eventId;
 private int participantCount;
 private Integer maxParticipants;

}
