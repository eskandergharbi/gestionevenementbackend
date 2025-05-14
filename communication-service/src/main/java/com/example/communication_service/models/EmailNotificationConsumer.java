package com.example.communication_service.models;
//com.example.communication_service.models.EmailNotificationConsumer
import com.example.communication_service.config.RabbitMQConfig;
import com.example.communication_service.dto.UserDTO;
import com.example.communication_service.services.EmailService;
import com.example.communication_service.services.UserClient;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailNotificationConsumer {

 @Autowired
 private EmailService emailService;

 @Autowired
 private UserClient userClient;

 @RabbitListener(queues = RabbitMQConfig.QUEUE)
 public void consume(AnnouncementMessage message) {
     List<UserDTO> users = userClient.getAllUsers(0, 1000); // Get first 1000 users

     for (UserDTO user : users) {
         emailService.sendEmail(
             user.getEmail(),
             "Announcement " + message.getAction(),
             message.getTitle() + "\n\n" + message.getContent()
         );
     }
 }
}
