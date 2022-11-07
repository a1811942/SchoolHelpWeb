package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Commission;
import com.example.demo.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommissionDao extends BaseMapper<Commission> {
    //查询学生表和委托表

    public List<Map<String ,Object>> getCommissionAndStudent(
                                                             @Param("orderType")String orderType,
                                                             @Param("orderField") String orderField,
                                                             @Param("status")String status);

    Boolean setStatusOne(@Param("commissionId") String commissionId,
                         @Param("acceptName") String acceptName,
                         @Param("acceptStudentId") String acceptStudentId);
    Map<String ,Object>  getCommissionAndStudentBycommissionId(String commissionId);
    //-个人信息中的
    List<Map<String ,Object>> getCommissionAndStudentByStudentId(String studentId);
    Student getAcceptStudentByAcceptStudentId(String acceptStudentId);

}
