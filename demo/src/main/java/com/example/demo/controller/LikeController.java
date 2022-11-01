package com.example.demo.controller;

import com.example.demo.common.R.Result;
import com.example.demo.entity.PointLike;
import com.example.demo.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/demo/like")
public class LikeController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LikeService likeService;
    /**
     * 点赞
     * @param pointLike
     * @return
     */
    @PostMapping("/doLike")
    public Result<Map<String, String>> doLike(@RequestBody PointLike pointLike){
        String momentsId = pointLike.getMomentsId();
        String studentId = pointLike.getStudentId();
        Map<String, String> map = new HashMap<>();
        map.put("momentsId",momentsId);
        String  result=null;
        String key="like:"+momentsId;
        Long res = redisTemplate.opsForSet().add(key, studentId);
        if (res==1){
            //点赞
            result="1";
            map.put("result",result);

        }
        else {
            //取消点赞
            result="0";
            redisTemplate.opsForSet().remove(key,studentId);
        }
        return Result.getSuccessResult(map);

    }

    /**
     * 取消点赞
     * @param pointLike
     * @return
     */
    @PostMapping("/undoLike")
    public Result<String > undoLike(@RequestBody PointLike pointLike){
        String momentsId = pointLike.getMomentsId();
        String studentId = pointLike.getStudentId();
        String result="";
        String key="like:"+momentsId;
        Long res = redisTemplate.opsForSet().remove(key,studentId);
        if (res==1){
            result="取消成功";
        }
        else {
            result="你已取消点赞";
        }
        return Result.getMessageResult(result,"A001");

    }
    @PostMapping("/getLike")
    public Result<String > getLike(@RequestBody PointLike pointLike){
        String momentsId = pointLike.getMomentsId();
        String studentId = pointLike.getStudentId();
        String result="";
        String key="like:"+momentsId;
        Long res = redisTemplate.opsForSet().size(key);
        Boolean res2 = redisTemplate.opsForSet().isMember(key,studentId);
        if (res==1){
            result="取消成功";
        }
        else {
            result="你已取消点赞";
        }
        return Result.getMessageResult(result,"A001");

    }

    /**
     *根据动态id查询点赞数
     * @param pointLike
     * @return
     */
    @PostMapping("/getLikeCount")
    public Result<Integer> getLikeCount(@RequestBody PointLike pointLike){
        String momentsId = pointLike.getMomentsId();
        String studentId = pointLike.getStudentId();
        String key="like:"+momentsId;
        int likeCount = likeService.getLikeCount(key);
        return Result.getSuccessResult(likeCount);

    }
}
