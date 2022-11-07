package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.dto.CommissionDto;
import com.example.demo.entity.Commission;
import com.example.demo.entity.Student;


import java.util.List;
import java.util.Map;

public interface CommissionService extends IService<Commission> {
    public List<Map<String ,Object>> getCommissionAndStudent(CommissionDto commissionDto);
    //状态设置为1表示已被接单 设置接单人的id
    Boolean setStatusOne(String commissionId,String acceptName,String acceptStudentId);

    Boolean saveCommission(Commission commission);
    Boolean updateCommission(Commission commission);
    Boolean deleteCommissionById(String commissionId);
    Map<String ,Object>  getCommissionAndStudentById(String commissionId);
    Student getStudentById(String   acceptStudentId);
    List<Map<String ,Object>> getCommissionAndStudentByStudentId(String studentId);

    Student getAcceptStudentByAcceptStudentId(String acceptStudentId);
}
