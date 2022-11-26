package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Follow;

public interface FollowService extends IService<Follow> {
    //点关注
    Integer PointFollow(Follow follow);
    //查看是否关注
    String isPointStudent(Follow follow);
    //根据当前用户查看关注的人数的总数量
    Integer getFollowCount(String selfStudentId);
    //根据当前用户查看 粉丝数量
    Integer getFansCount(String followStudentId);
}
