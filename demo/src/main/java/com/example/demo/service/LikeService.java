package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.UserLike;

import java.util.List;

public interface LikeService extends IService<UserLike> {
    int getLikeCount(String key);

}
