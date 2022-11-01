package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.util.DetermineTime;
import com.example.demo.dto.CommissionDto;
import com.example.demo.entity.Commission;
import com.example.demo.entity.Student;
import com.example.demo.mapper.CommissionDao;
import com.example.demo.mapper.StudentDao;
import com.example.demo.service.CommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 委托业务逻辑
 */

@Service
public class CommissionServiceImpl extends ServiceImpl<CommissionDao, Commission> implements CommissionService {
    @Autowired
    private CommissionDao commissionDao;

    @Autowired
    private StudentDao studentDao;
    /**
     * 查询委托信息 包括 commission委托表 student 学生表
     * @param commissionDto
     * @return
     */
    @Override
    public List<Map<String, Object>> getCommissionAndStudent(CommissionDto commissionDto) {
        String substring = commissionDto.getOrderType();
        int length = substring.length();
        String status = commissionDto.getStatus();
        String orderField = null;
        String orderType=null;

        if (substring!=null && ""!=substring) {
            if (substring.substring(0, 1).equals("a")) {
                orderType = substring.substring(0, 3);
                orderField = substring.substring(3, length);

            }
            if (substring.substring(0, 1).equals("d")) {
                orderType = substring.substring(0, 4);
                orderField = substring.substring(4, length);
            }
        }
        List<Map<String, Object>> list = commissionDao.getCommissionAndStudent(orderType,orderField,status);
        if (list.size()>1){
            for (Map<String, Object> map : list) {
                Timestamp dateTime = (Timestamp) map.get("date");
                String s = DetermineTime.showDate(dateTime, "yyyy年MM月dd日HH:mm");
                map.put("date",s);

            }
        }
        return list;
    }

    /**
     * 将委托状态设置为1 代表已被接单 并添加接单人id到commission表中
     * @param commissionId
     * @return
     */
    @Override
    public Boolean setStatusOne(String commissionId,String studentId) {
        Commission commission = this.getById(commissionId);
        String status = commission.getStatus();
        if (status.equals("1")){
            return false;
        }
        Boolean res = commissionDao.setStatusOne(commissionId,studentId);
        return res;
    }

    @Override
    public Boolean SaveCommission(Commission commission) {
        commission.setId(UUID.randomUUID().toString());
        commission.setDate(Timestamp.valueOf(LocalDateTime.now()));
        boolean res = this.save(commission);
        return res;
    }

    @Override
    public Map<String, Object> getCommissionAndStudentById(String commissionId) {
        Map<String, Object> res = commissionDao.getCommissionAndStudentBycommissionId(commissionId);
        return res;
    }

    @Override
    public Student getStudentById(String studentId) {
        Student student = studentDao.selectById(studentId);
        return student;
    }

    @Override
    public List<Map<String ,Object>> getCommissionAndStudentByStudentId(String studentId) {
        List<Map<String ,Object>> res = commissionDao.getCommissionAndStudentByStudentId(studentId);
        return res;
    }
}
