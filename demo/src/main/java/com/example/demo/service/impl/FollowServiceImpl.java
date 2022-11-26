package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.R.Result;
import com.example.demo.entity.Follow;
import com.example.demo.mapper.FollowDao;
import com.example.demo.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 关注
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowDao, Follow> implements FollowService {

    @Autowired
    private FollowDao followDao;

    /**
     * 点关注
     *
     * @param follow
     * @return
     */
    @Override
    public Integer PointFollow(Follow follow) {
        List<Follow> list = followDao.getFollowByPointStudentId(follow.getFollowStudentId());
        for (Follow follow1 : list) {
            String selfStudentId = follow1.getSelfStudentId();

            //如果已经关注了
            if(selfStudentId.equals(follow.getSelfStudentId())){
                //取消关注（删除被关注人id的记录）
                LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Follow::getSelfStudentId,follow.getSelfStudentId());
                this.remove(queryWrapper);
                return -1;
            }
        }
        //没有关注就保存
        follow.setId(UUID.randomUUID().toString());
        follow.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        int res = followDao.insert(follow);

        return res;
    }

    /**
     * 查看是否已关注此用户
     * @param follow
     * @return
     */
    @Override
    public String isPointStudent(Follow follow) {
        List<Follow> list = followDao.getFollowByPointStudentId(follow.getFollowStudentId());
        for (Follow follow1 : list) {
            String selfStudentId = follow1.getSelfStudentId();

            //如果已经关注了
            if(selfStudentId.equals(follow.getSelfStudentId())){
                return "1";
            }
        }
        //未关注
        return "2";
    }

    /**
     * 根据当前用户查看关注的人数的总数量
     * @param selfStudentId
     * @return
     */
    @Override
    public Integer getFollowCount(String selfStudentId) {
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getSelfStudentId,selfStudentId);
        Integer count = followDao.selectCount(queryWrapper);
        return count;
    }

    /**
     * 根据当前用户查看 粉丝的人数的总数量
     * @param followStudentId
     * @return
     */
    @Override
    public Integer getFansCount(String followStudentId) {
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getFollowStudentId,followStudentId);
        Integer count = followDao.selectCount(queryWrapper);
        return count;
    }
}
