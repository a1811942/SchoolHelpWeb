package com.example.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 用于接受前端数据
 */
@Data
public class CommissionDto {
    private String orderType;
    private Timestamp date;
    private String status;
    private String  orderField;
    private String studentId;
    private  String commissionId;
    private String money;
    private String limitSex;
    private String content;
}
