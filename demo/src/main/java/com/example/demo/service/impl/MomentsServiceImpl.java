package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.util.DetermineTime;
import com.example.demo.entity.Moments;
import com.example.demo.entity.Photo;
import com.example.demo.mapper.MomentsDao;
import com.example.demo.mapper.PhotoDao;
import com.example.demo.service.LikeService;
import com.example.demo.service.MomentsService;
import io.netty.util.internal.UnstableApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 朋友圈业务逻辑
 */
@Service
public class MomentsServiceImpl extends ServiceImpl<MomentsDao, Moments> implements MomentsService {
    @Autowired
    private MomentsDao momentsDao;
    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private LikeService likeService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 查询朋友圈所有人的动态信息
     * @return
     */
    @Override
    public List<Map<String ,Object>> getMomentsStudent() {
        List<Map<String ,Object>> list = momentsDao.getMomentsAndStudent();//查询所有动态

        for ( Map<String, Object> stringObjectMap : list) {//遍历
            Timestamp date = (Timestamp) stringObjectMap.get("date");
            String  momentsId = (String) stringObjectMap.get("id");//获取动态id
            String studentId = (String) stringObjectMap.get("studentId");
            List<String> photoList = photoDao.getPhotoByMomentsId(momentsId);//根据动态id和phoho表查出photo集合
            stringObjectMap.put("photoName",photoList);//放到map中
            String s = DetermineTime.showDate(date, "yyyy年MM月dd日HH:mm");
            stringObjectMap.put("date",s);
            String key="like:"+momentsId;
            int likeCount = likeService.getLikeCount(key);
            stringObjectMap.put("likeCount",likeCount);
        }
        return list;
    }

    /**
     * 根据momentId查询某个动态
     * @param momentsId
     * @return
     */
    @Override
    public Map<String, Object> getMomentsAndStudentById(String momentsId) {
        Map<String, Object> map = momentsDao.getMomentsAndStudentById(momentsId);
        //        String momentsId = moments.getId();
//        System.out.println("momentsId====="+momentsId);
        Timestamp date = (Timestamp) map.get("date");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String dateString = simpleDateFormat.format(date);
        map.put("date", dateString);
        return map;
    }

    @Override
    public String saveMoments(Moments moments) {
        moments.setDate (Timestamp.valueOf(LocalDateTime.now()));
        String monentsId=UUID.randomUUID().toString();
        moments.setId(monentsId);
        Boolean res = momentsDao.saveMoments(moments);
        if (res){
            return monentsId;
        }
       return "插入失败";
    }
}
