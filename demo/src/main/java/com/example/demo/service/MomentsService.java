package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Moments;

import java.util.List;
import java.util.Map;

public interface MomentsService extends IService<Moments> {
    public List<Map<String ,Object>> getMomentsStudent();
    public Map<String ,Object> getMomentsAndStudentById(String id);
    String saveMoments(Moments moments);
}
