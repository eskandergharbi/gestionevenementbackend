package com.example.communication_service.models;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.communication_service.config.RabbitMQConfig;

@Service
public class AnnouncementPublisher {

    @Autowired
    private AmqpTemplate template;

    public void publish(AnnouncementMessage message) {
        template.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            RabbitMQConfig.ROUTING_KEY,
            message
        );
    }
}

