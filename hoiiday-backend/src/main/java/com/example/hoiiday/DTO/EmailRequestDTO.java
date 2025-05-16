package com.example.hoiiday.DTO;

import lombok.Data;

@Data
public class EmailRequestDTO {
 private String to;
 private String subject;
 private String body;
}