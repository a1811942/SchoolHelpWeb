package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Moments;

import java.util.List;
import java.util.Map;

public interface MomentsService extends IService<Moments> {
    //查询所有动态
    public List<Map<String ,Object>> getMomentsStudent();
    //根据动态id查询动态
    public Map<String ,Object> getMomentsAndStudentById(String id);
    //保存东团
    String saveMoments(Moments moments);
    //删除动态
    Boolean deleteMoments(String momentsId);
    List<Map<String, Object>> getMomentsAndStudentByStuId(String studentId);
}
