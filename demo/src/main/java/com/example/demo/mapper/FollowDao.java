package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Follow;

import java.util.List;

public interface FollowDao extends BaseMapper<Follow> {
    List<Follow> getFollowByPointStudentId(String followStudentId);
}
