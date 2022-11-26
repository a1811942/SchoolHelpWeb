package com.example.demo.controller;

import com.example.demo.common.R.Result;
import com.example.demo.entity.Follow;
import com.example.demo.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("demo/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    /**
     * 点关注
     * @param follow
     * @return
     */
    @PutMapping("/PointFollow")
    public Result<Integer> PointFollow(@RequestBody  Follow follow){
        Integer res = followService.PointFollow(follow);
        return Result.getSuccessResult(res);
    }

    /**
     * 查看是否已关注此用户
     * @param follow
     * @return
     */
    @PostMapping("/isPointStudent")
    public Result<String> isPointStudent(@RequestBody  Follow follow){
        String res = followService.isPointStudent(follow);
        return Result.getSuccessResult(res);
    }

    /**
     * 根据当前用户查看关注的人数的总数量
     * @param selfStudentId
     * @return
     */
    @PostMapping("/getFollowCount")
    public Result<Integer> getFollowCount(@RequestBody  String selfStudentId){
        Integer count = followService.getFollowCount(selfStudentId);
        return Result.getSuccessResult(count);
    }

    /**
     * 根据当前用户查看 粉丝的人数的总数量
     * @param selfStudentId
     * @return
     */
    @PostMapping("/getFansCount")
    public Result<Integer> getFansCount(@RequestBody  String followStudentId){
        Integer count = followService.getFansCount(followStudentId);
        return Result.getSuccessResult(count);
    }
}
