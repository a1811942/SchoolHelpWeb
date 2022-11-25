package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Follow;

public interface FollowService extends IService<Follow> {
    Integer PointFollow(Follow follow);
    String isPointStudent(Follow follow);
}
