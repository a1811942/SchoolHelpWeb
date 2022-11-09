package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

public interface StudentDao extends BaseMapper<Student> {
    public List<Student> getEmails();
    //修改头像
    Boolean updateAvatarByStudentId(@Param("avatar") String avatar,@Param("studentId") String StudentId);

    //修改资料
    Boolean updateStudentById(@Param("id")String id,
                              @Param("name")String name,
                              @Param("address")String address,
                              @Param("age") Integer age,
                              @Param("birthday")Date birthday,
                              @Param("enrollmentTime")Date enrollmentTime,
                              @Param("sex")String  sex,
                              @Param("signature")String signature,
                              @Param("school")String school);
}
