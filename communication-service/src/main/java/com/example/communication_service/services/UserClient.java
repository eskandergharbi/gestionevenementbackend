package com.example.communication_service.services;

import com.example.communication_service.dto.CustomPage;
//com.example.communication_service.services.UserClient
import com.example.communication_service.dto.UserDTO;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserClient {

 private final RestTemplate restTemplate;

 @Value("${user.service.url}")
 private String userServiceUrl;

 public UserClient(RestTemplate restTemplate) {
     this.restTemplate = restTemplate;
 }

 public List<UserDTO> getAllUsers(int page, int size) {
     String url = String.format("%s?page=%d&size=%d", userServiceUrl, page, size);

     ResponseEntity<CustomPage<UserDTO>> response = restTemplate.exchange(
             url,
             HttpMethod.GET,
             null,
             new ParameterizedTypeReference<CustomPage<UserDTO>>() {}
     );

     return response.getBody().getContent();
 }
}
