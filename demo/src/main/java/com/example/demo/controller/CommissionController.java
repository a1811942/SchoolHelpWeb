package com.example.demo.controller;

import com.example.demo.common.R.Result;
import com.example.demo.common.util.DetermineTime;
import com.example.demo.dto.CommissionDto;
import com.example.demo.entity.Commission;
import com.example.demo.entity.Student;
import com.example.demo.mapper.CommissionDao;
import com.example.demo.service.CommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 委托控制器
 */
@RestController
@RequestMapping("/demo/commission")
public class CommissionController{
    @Autowired
    private CommissionService commissionService;

    @Autowired
    private CommissionDao commissionDao;

    /**
     * 查询所有委托
     * @param request
     * @param commissionDto
     * @return
     */
    @PostMapping("/getCommissionAndStudent")
    public Result<List<Map<String, Object>>> getCommissionAndStudent(HttpServletRequest request, @RequestBody CommissionDto commissionDto) {
        List<Map<String, Object>> list = commissionService.getCommissionAndStudent(commissionDto);
        HttpSession session = request.getSession();
        return Result.getSuccessResult(list);
    }

    /**
     * 接受委托
     * @param commission
     * @return
     */
    @PutMapping("/setStatusOne")
    public Result<Boolean> setStatusOne(@RequestBody Commission  commission) {
        String commissionId = commission.getId();
        String studentId = commission.getStudentId();
        Boolean res = commissionService.setStatusOne(commissionId,studentId);
        if (res){
            return Result.getMessageResult(res,"A001");
        }
        return Result.getMessageResult(res,"A002");
    }

    /**
     * 发布委托
     * @param commission
     * @return
     */
    @PutMapping("/SaveCommission")
    public Result<String > SaveCommission(@RequestBody Commission  commission) {
        BigDecimal money = commission.getMoney();

        Boolean res = commissionService.SaveCommission(commission);
        if (res){
            return Result.getMessageResult("success","A001");

        }
        return Result.getMessageResult("error","A002");
    }

    /**
     * 根据委托id查询详细信息
     * @param commissionId
     * @return
     */
    @PostMapping("/getCommissionAndStudentBycommissionId")
    public Result<Map<String ,Object>> getCommissionAndStudentBycommissionId(@RequestBody String commissionId) {
        Map<String, Object> res = commissionService.getCommissionAndStudentById(commissionId);
        return Result.getSuccessResult(res);
    }

    @PostMapping("/getStudentById")
    public Result<Student> getStudentById(@RequestBody String studentId) {
        Student res = commissionService.getStudentById(studentId);
        return Result.getSuccessResult(res);
    }

    /**
     * 根据用户id查询委托
     * @param studentId
     * @return
     */
    @PostMapping("/getCommissionAndStudentByStudentId")
    public Result<List<Map<String ,Object>>> getCommissionAndStudentByStudentId(@RequestBody String studentId) {
        List<Map<String ,Object>> res = commissionService.getCommissionAndStudentByStudentId(studentId);
        return Result.getSuccessResult(res);
    }
}
