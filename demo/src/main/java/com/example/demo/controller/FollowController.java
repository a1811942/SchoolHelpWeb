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
}
