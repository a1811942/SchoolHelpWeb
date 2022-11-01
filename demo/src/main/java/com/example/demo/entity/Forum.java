package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Forum {
    private String id;
    @TableField("student_id")
    private String studentId;
    private Timestamp date;
    @TableField("forum_content")
    private String forumContent;
    private String title;
}
