package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Forum;
import com.example.demo.entity.ForumDoc;
import com.github.pagehelper.PageInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ForumService extends IService<Forum> {
    PageInfo< Map<String ,Object>> getALlForumAndStudent(Integer pageNum,Integer pageSize);
    Boolean saveForum(Forum forum) throws IOException;
    Boolean deleteForum(String  forumId);
    Boolean updateForum(Forum forum);

    List<ForumDoc> getForumDoc();
    ForumDoc getForumDocById(String forumId);
}
