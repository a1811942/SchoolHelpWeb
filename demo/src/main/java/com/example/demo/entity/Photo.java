package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 照片表
 */
@Data
public class Photo {
    private String id;
    @TableField("photoName")
    private String photoName;
    @TableField("moments_Id")
    private String momentsId;
    @TableField("comment_Id")
    private String commentId;
}
