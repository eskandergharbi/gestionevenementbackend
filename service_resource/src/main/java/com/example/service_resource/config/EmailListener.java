package com.example.service_resource.config;

import com.example.communication_service.dto.EmailBroadcastDTO;
import com.example.communication_service.models.User;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class EmailListener {

    @Autowired
    private JavaMailSender mailSender;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String USER_SERVICE_URL = "http://localhost:8088/api/users/all";

    @RabbitListener(queues = "email_queue")
    public void broadcastEmail(EmailBroadcastDTO dto) {
        // Directly get a List of User
        User[] usersArray = restTemplate.getForObject(USER_SERVICE_URL, User[].class);
        List<User> users = Arrays.asList(usersArray);

        for (User user : users) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject(dto.getSubject());
            message.setText(dto.getBody());
            mailSender.send(message);
        }
    }
}
