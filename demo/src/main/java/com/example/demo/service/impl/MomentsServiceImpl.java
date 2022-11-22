package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.util.DetermineTime;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Moments;
import com.example.demo.entity.Photo;
import com.example.demo.mapper.CommentDao;
import com.example.demo.mapper.MomentsDao;
import com.example.demo.mapper.PhotoDao;
import com.example.demo.service.*;
import io.netty.util.internal.UnstableApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private CommentDao commentDao;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private UpdateAndDownService updateAndDownService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private RedisTemplate redisTemplate;


    @Value("${photo.path}")
    private String basePath;

    /**
     * 查询朋友圈所有人的动态信息
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getMomentsStudent() {
        List<Map<String, Object>> list = momentsDao.getMomentsAndStudent();//查询所有动态
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();


        for (Map<String, Object> stringObjectMap : list) {//遍历
            Timestamp date = (Timestamp) stringObjectMap.get("date");
            String momentsId = (String) stringObjectMap.get("id");//获取动态id
            String studentId = (String) stringObjectMap.get("studentId");
            List<String> photoList = photoService.getPhotoByMomentsId(momentsId);//根据动态id和phoho表查出photo集合
            stringObjectMap.put("photoName", photoList);//放到map中
            String s = DetermineTime.showDate(date, "yyyy年MM月dd日HH:mm");
            //根据动态id获取评论数量
            queryWrapper.eq(Comment::getMomentsId,momentsId);
            Integer count = commentDao.selectCount(queryWrapper);
            stringObjectMap.put("count", count);

            stringObjectMap.put("date", s);
            String key = "like:" + momentsId;
            int likeCount = likeService.getLikeCount(key);
            stringObjectMap.put("likeCount", likeCount);
        }
        return list;
    }

    /**
     * 根据momentId查询某个动态
     *
     * @param momentsId
     * @return
     */
    @Override
    public Map<String, Object> getMomentsAndStudentById(String momentsId) {
        Map<String, Object> map = momentsDao.getMomentsAndStudentById(momentsId);
        //修改时间格式
        Timestamp date = (Timestamp) map.get("date");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String dateString = simpleDateFormat.format(date);
        //查询点赞数量
        String key = "like:" + momentsId;
        int likeCount = likeService.getLikeCount(key);
        //根据动态id获取评论数量
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getMomentsId,momentsId);
        Integer count = commentDao.selectCount(queryWrapper);
        map.put("commentCount", count);
        map.put("date", dateString);
        map.put("likeCount", likeCount);
        return map;
    }

    /**
     * 发布动态
     * @param moments
     * @return
     */
    @Override
    public String saveMoments(Moments moments) {
        moments.setDate(Timestamp.valueOf(LocalDateTime.now()));
        moments.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        String monentsId = UUID.randomUUID().toString();
        moments.setId(monentsId);
        Boolean res = momentsDao.saveMoments(moments);
        if (res) {
            return monentsId;
        }
        return "插入失败";
    }

    /**
     * 删除动态 照片 本地照片
     *
     * @param momentsId
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteMoments(String momentsId) {
        List<String> photo = photoService.getPhotoByMomentsId(momentsId);

        for (String s : photo) {
            updateAndDownService.deletePhoto(s);
        }
        boolean res = this.removeById(momentsId);
        Boolean res1 = photoService.deleteByMomentsId(momentsId);
        Boolean res2 = commentService.deleteByMomentsId(momentsId);
        if (res && res1 &&res2) {
            return true;
        }
        return false;
    }

    /**
     * 根据学生id查询所有动态
     * @param studentId
     * @return
     */
    @Override
    public List<Map<String, Object>> getMomentsAndStudentByStuId(String id) {
        List<Map<String, Object>> list = momentsDao.getMomentAndStudentByStuId(id);//根据学生id查询
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();


        for (Map<String, Object> stringObjectMap : list) {//遍历
            Timestamp date = (Timestamp) stringObjectMap.get("date");
            String momentsId = (String) stringObjectMap.get("id");//获取动态id
            String studentId = (String) stringObjectMap.get("studentId");
            List<String> photoList = photoService.getPhotoByMomentsId(momentsId);//根据动态id和phoho表查出photo集合
            stringObjectMap.put("photoName", photoList);//放到map中
            String s = DetermineTime.showDate(date, "yyyy年MM月dd日HH:mm");
            //根据动态id获取评论数量
            queryWrapper.eq(Comment::getMomentsId,momentsId);
            Integer count = commentDao.selectCount(queryWrapper);
            stringObjectMap.put("count", count);

            stringObjectMap.put("date", s);
            String key = "like:" + momentsId;
            int likeCount = likeService.getLikeCount(key);
            stringObjectMap.put("likeCount", likeCount);
        }
        return list;
    }
}
