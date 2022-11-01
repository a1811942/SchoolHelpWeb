package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Forum;
import com.example.demo.entity.ForumDoc;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface ForumDao extends BaseMapper<Forum> {
    List<Map<String ,Object>> getALlForumAndStudent();
    List<ForumDoc> getForumDoc();
}
