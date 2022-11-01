package com.example.demo.dto;

import lombok.Data;

@Data
public class StuMessageDto {
    private String studentId;
    private String message;
    private String toStudentId;
}
