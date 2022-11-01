package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UserLike {
    private String id;
    @TableField("student_id")
    private String studentId;
    @TableField("moments_id")
    private String momentsId;
}
