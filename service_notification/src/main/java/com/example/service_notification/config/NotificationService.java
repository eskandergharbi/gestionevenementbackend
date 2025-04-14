package com.example.service_notification.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.service_participant.model.Notification;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "${kafka.topic.notification}", groupId = "notification-group",containerFactory = "kafkaListenerContainerFactory")
    public void sendEmailNotification(Notification notification) {
        log.debug("Received notification for email: {}", notification.getEmail()); // Add debug log
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getEmail());
        message.setSubject("Notification d'inscription");
        message.setText(notification.getMessage());
        
        mailSender.send(message);
    }
}
