package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.dto.CommissionDto;
import com.example.demo.entity.Commission;
import com.example.demo.entity.Student;


import java.util.List;
import java.util.Map;

public interface CommissionService extends IService<Commission> {
    public List<Map<String ,Object>> getCommissionAndStudent(CommissionDto commissionDto);
    Boolean setStatusOne(String commissionId,String studentId);
    Boolean SaveCommission(Commission commission);
    Map<String ,Object>  getCommissionAndStudentById(String commissionId);
    Student getStudentById(String studentId);
    List<Map<String ,Object>> getCommissionAndStudentByStudentId(String studentId);

}
