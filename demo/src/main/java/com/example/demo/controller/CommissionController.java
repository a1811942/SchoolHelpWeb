package com.example.demo.controller;

import com.example.demo.common.R.Result;
import com.example.demo.common.util.DetermineTime;
import com.example.demo.dto.CommissionDto;
import com.example.demo.entity.Commission;
import com.example.demo.entity.Student;
import com.example.demo.mapper.CommissionDao;
import com.example.demo.service.CommissionService;
import org.apache.ibatis.annotations.Update;
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
    @PostMapping("/setStatusOne")
    public Result<Boolean> setStatusOne(@RequestBody Commission  commission) {
        String commissionId = commission.getId();
        String acceptStudentId = commission.getAcceptStudentId();
        String acceptName = commission.getAcceptName();
        Boolean res = commissionService.setStatusOne(commissionId,acceptName,acceptStudentId);
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
    @PostMapping("/SaveCommission")
    public Result<String > saveCommission(@RequestBody Commission  commission) {
        BigDecimal money = commission.getMoney();

        Boolean res = commissionService.saveCommission(commission);
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

    /**
     * 修改-个人信息
     * @param commission
     * @return
     */
    @PutMapping("/updateCommission")
    public Result<String > updateCommission(@RequestBody Commission  commission) {
        Boolean res = commissionService.updateCommission(commission);
        if (res){
            return Result.getMessageResult("success","A001");

        }
        return Result.getMessageResult("error","A002");
    }

    /**
     * 删除-个人信息
     * @param commissionId
     * @return
     */
    @PostMapping("/deleteCommissionById")
    public Result<String > deleteCommissionById(@RequestBody String  commissionId) {
        Boolean res = commissionService.deleteCommissionById(commissionId);
        if (res){
            return Result.getMessageResult("success","A001");

        }
        return Result.getMessageResult("error","A002");
    }

    /**
     * 查询接单人信息
     * @param acceptStudentId
     * @return
     */
    @PostMapping("/getAcceptStudentByAcceptStudentId")
    public Result<Student> getAcceptStudentByAcceptStudentId(@RequestBody String  acceptStudentId) {
        Student student = commissionService.getAcceptStudentByAcceptStudentId(acceptStudentId);

        return Result.getSuccessResult(student);
    }
}
