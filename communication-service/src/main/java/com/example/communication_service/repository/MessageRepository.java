package com.example.communication_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.communication_service.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByEventId(Long eventId);
}
