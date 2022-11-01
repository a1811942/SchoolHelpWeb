package com.example.demo.controller;

import com.example.demo.common.R.Result;
import com.example.demo.dto.StuMessageDto;
import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/demo/redis")
@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;

    @PostMapping("/saveMessage")
    public void saveMessage(@RequestBody StuMessageDto stuMessageDto){
        redisService.saveMessage(stuMessageDto);
    }

    @PostMapping("/getMessageByKey")
    public Result<Map<String,Object >> getMessageByKey(@RequestBody StuMessageDto stuMessageDto){
        Map<String, Object> map = redisService.getMessageByKey(stuMessageDto);
        return Result.getSuccessResult(map);
    }

}
