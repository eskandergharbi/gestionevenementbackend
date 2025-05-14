package com.example.communication_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.communication_service.models.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
	
}
