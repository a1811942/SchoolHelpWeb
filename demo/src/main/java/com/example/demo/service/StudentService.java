package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.R.Result;
import com.example.demo.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService extends IService<Student> {
    public Map<String ,String > login(String username,String password);
    public Result<Map<String ,String >> register(Student student, String code);
    Boolean update(Student student);
    Student getStudentById(String studentId);
    Student select(String username);
}
