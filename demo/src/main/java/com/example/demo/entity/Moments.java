package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 动态表
 */
@Data
public class Moments {
    private String id;
    @TableField("student_id")
    private String studentId;
    private String image;
    private String content;
    private Timestamp date;
    @TableField("comment_id")
    private String commentId;
    @TableField("like_count")
    private int likeCount;
    @TableField("update_time")
    private Timestamp updateTime;


}
