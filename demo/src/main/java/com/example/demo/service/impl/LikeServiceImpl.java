package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.UserLike;
import com.example.demo.mapper.LikeDao;
import com.example.demo.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.Set;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeDao, UserLike> implements LikeService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public int getLikeCount(String key ) {
        Set members = redisTemplate.opsForSet().members(key);
        int size = members.size();
        return size;
    }


}
