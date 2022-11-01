package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 评论表
 */
@Data
public class Comment {
    private String id;
    private String comment;
    @TableField("student_id")
    private String studentId;
    @TableField("student_name")
    private String studentName;
    @TableField("moments_id")
    private String momentsId;
    private Timestamp date;
}
