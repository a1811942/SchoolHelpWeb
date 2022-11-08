package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentDao extends BaseMapper<Student> {
    public List<Student> getEmails();
    //修改头像
    Boolean updateAvatarByStudentId(@Param("avatar") String avatar,@Param("studentId") String StudentId);

}
