package com.example.demo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PhotoDto {
    private String[] photoName;
    private String momentsId;
    private String id;
    private String commentId;
    private Timestamp date;
}
