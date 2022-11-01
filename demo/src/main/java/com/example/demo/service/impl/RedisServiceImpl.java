package com.example.demo.service.impl;

import com.example.demo.dto.StuMessageDto;
import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 保存聊天记录
     * @param stuMessageDto
     */
    @Override
    public void saveMessage(StuMessageDto stuMessageDto) {
        String key="message:"+stuMessageDto.getStudentId()+":"+stuMessageDto.getToStudentId();
        redisTemplate.opsForSet().add(key,stuMessageDto.getMessage());
    }

    /**
     * 根据用户id和对话用户id查询信息
     * @param stuMessageDto
     * @return
     */
    @Override
    public Map<String,Object > getMessageByKey(StuMessageDto stuMessageDto) {
        String key1="message:"+stuMessageDto.getStudentId()+":"+stuMessageDto.getToStudentId();
        String key2="message:"+stuMessageDto.getToStudentId()+":"+stuMessageDto.getStudentId();
       Set set1 =  redisTemplate.boundSetOps(key1).members();
       Set set2 =  redisTemplate.boundSetOps(key2).members();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.addAll(set1);
        list2.addAll(set2);

        HashMap<String, Object> map = new HashMap<>();
        map.put("myMessage",list1);
        map.put("yourMessage",list2);
        System.out.println(map);
        return map;
    }
}
