package com.example.demo.service;

import com.example.demo.dto.StuMessageDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {
    void saveMessage(StuMessageDto stuMessageDto);
    Map<String,Object > getMessageByKey(StuMessageDto stuMessageDto);
}
