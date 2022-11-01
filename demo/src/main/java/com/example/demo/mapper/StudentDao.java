package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Student;

import java.util.List;

public interface StudentDao extends BaseMapper<Student> {
    public List<Student> getEmails();
}
