package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentDao extends BaseMapper<Comment> {
    //根据动态id查询所有评论
    public List<Map<String ,Object>> getCommentAndStudentByMomentId(String momentId);
    //根据动态id删除所有评论
    Boolean deleteByMomentsId(String momentsId);
}
