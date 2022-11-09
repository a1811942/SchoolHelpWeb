package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 用户表
 */
@Data
@TableName("student")
public class Student {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;
    private String username;
    private String password;
    @TableField("enrollment_time")
    private Date enrollmentTime;
    private String email;
    private Date birthday;
    private String name;
    private String school;
    private String sex;
    private String address;
    private String phone;
    @TableField("school_address")
    private String schoolAddress;
    private String avatar;
    private String signature;
    private Integer age;

}
