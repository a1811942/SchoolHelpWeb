package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 * 委托表
 */
@Data
public class Commission {
    private String id;
    @TableField("student_id")
    private String studentId;
    private Timestamp date;
    private BigDecimal money;
    private String status;

    @TableField("limit_sex")
    private String limitSex;
    private String content;
    @TableField("employee_id")
    private String employeeId;

    public Commission() {
    }
}
