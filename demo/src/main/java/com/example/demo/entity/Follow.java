package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 关注
 */
@Data
public class Follow {
    private String id;
    @TableField("self_student_id")
    private String selfStudentId;
    @TableField("follow_student_id")
    private String followStudentId;
    @TableField("create_time")
    private Timestamp createTime;
}
