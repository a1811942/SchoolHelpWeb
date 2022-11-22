package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Moments;

import java.util.List;
import java.util.Map;

public interface MomentsDao extends BaseMapper<Moments> {
    //查询photo表 moments表 student表 所有  查看所有动态
    public List<Map<String ,Object>> getMomentsAndStudent();
    //查询photo表 moments表 student表 根据momentId   查看动态详情
    public Map<String ,Object> getMomentsAndStudentById(String momentsId);
    //发布动态
    Boolean saveMoments(Moments moments);
    //根据学生id查询所有动态
    List<Map<String, Object>> getMomentAndStudentByStuId(String studentId);

}
