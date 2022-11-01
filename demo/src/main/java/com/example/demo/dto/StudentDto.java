package com.example.demo.dto;

import com.example.demo.entity.Student;
import lombok.Data;

@Data
public class StudentDto {
    private Student student;
    private String code;
}
