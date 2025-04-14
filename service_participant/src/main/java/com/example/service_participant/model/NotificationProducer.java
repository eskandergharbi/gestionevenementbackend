package com.example.service_participant.model;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class NotificationProducer {
    
    private final KafkaTemplate<String, Notification> kafkaTemplate;
    private static final String TOPIC_NAME = "notification-topic";

    public NotificationProducer(KafkaTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Notification notification) {
        kafkaTemplate.send(TOPIC_NAME, notification);
        log.info("Sending notification to Kafka: {}", notification);

    }
}
