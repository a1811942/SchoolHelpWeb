package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Forum;
import com.example.demo.entity.ForumDoc;

import java.util.List;
import java.util.Map;

public interface ForumService extends IService<Forum> {
    List<Map<String ,Object>> getALlForumAndStudent();
    List<ForumDoc> getForumDoc();
}
