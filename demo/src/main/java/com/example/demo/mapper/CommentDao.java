package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentDao extends BaseMapper<Comment> {
    public List<Map<String ,Object>> getCommentAndStudentByMomentId(String momentId);
}
