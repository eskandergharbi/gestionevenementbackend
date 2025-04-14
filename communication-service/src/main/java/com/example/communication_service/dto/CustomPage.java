package com.example.communication_service.dto;

//com.example.communication_service.models.CustomPage

import java.util.List;

public class CustomPage<T> {
 private List<T> content;
 public List<T> getContent() { return content; }
 public void setContent(List<T> content) { this.content = content; }
}
