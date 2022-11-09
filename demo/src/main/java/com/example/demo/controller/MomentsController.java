package com.example.demo.controller;

import com.example.demo.common.R.Result;
import com.example.demo.common.util.DetermineTime;
import com.example.demo.entity.Moments;
import com.example.demo.service.MomentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 动态
 */
@RestController
@RequestMapping("/demo/moments")
//@CrossOrigin
public class MomentsController {
    @Autowired
    private MomentsService momentsService;

    /**
     * 查询朋友圈动态信息
     *
     * @return
     */
    @PostMapping("/getMomentsAndStudent")
    public Result<List<Map<String, Object>>> getMomentsAndStudent(HttpServletRequest request) {
        List<Map<String, Object>> list = momentsService.getMomentsStudent();
        System.out.println("session=="+ request.getSession().getAttribute("studentId"));
        return Result.getSuccessResult(list);

    }

    /**
     * 查询动态的详细信息 根据动态的ID
     *
     * @param momentsId
     * @return
     */
    @PostMapping("/getMomentsAndStudentById")
    public Result<Map<String, Object>> getMomentsAndStudentById(@RequestBody String momentsId) {
        Map<String, Object> map = momentsService.getMomentsAndStudentById(momentsId);
        return Result.getSuccessResult(map);

    }

    /**
     * 发布动态
     * @param moments
     * @return
     */
    @PostMapping("/saveMoments")
    public Result<String > saveMoments(@RequestBody  Moments moments){
        String momentsId = momentsService.saveMoments(moments);

            return Result.getMessageResult(momentsId,"A001");

    }

    /**
     * 删除动态 照片 本地照片
     * @param momentsId
     * @return
     */
    @PostMapping("/deleteMoments")
    public Result<Boolean > deleteMoments(@RequestBody String momentsId){
        Boolean res = momentsService.deleteMoments(momentsId);

        return Result.getSuccessResult(res);

    }
}
