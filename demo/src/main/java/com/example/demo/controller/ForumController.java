package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.R.Result;
import com.example.demo.common.util.RestClientUtil;
import com.example.demo.entity.Forum;
import com.example.demo.entity.ForumDoc;
import com.example.demo.service.ForumService;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/demo/forum")
public class ForumController {
    @Autowired
    private ForumService forumService;

    @Autowired
    private RestHighLevelClient client;

    @PostMapping("/getALlForumAndStudent")
    public Result<PageInfo<Map<String, Object>>> getALlForumAndStudent(@RequestParam(defaultValue = "1") Integer pageNum,
                                                                   @RequestParam(defaultValue = "6") Integer pageSize) {
        PageInfo<Map<String, Object>> pageInfo = forumService.getALlForumAndStudent(pageNum, pageSize);
        return Result.getSuccessResult(pageInfo);
    }

    /**
     * 发布文章
     *
     * @param forum
     * @return
     * @throws IOException
     */
    @PutMapping("/saveForum")
    public Result<Boolean> saveForum(@RequestBody Forum forum) throws IOException {
        Boolean res = forumService.saveForum(forum);
        return Result.getSuccessResult(res);

    }

    @DeleteMapping("/deleteForum")
    public Result<Boolean> deleteForum(@RequestBody String forumId) {
        Boolean res = forumService.deleteForum(forumId);
        return Result.getSuccessResult(res);
    }

    @PutMapping("/updateForum")
    public Result<Boolean> updateForum(@RequestBody Forum forum) {
        Boolean res = forumService.updateForum(forum);
        return Result.getSuccessResult(res);
    }

    @PutMapping("/updateForum1")
    public  void saveAll() throws IOException {
       RestClientUtil.saveAll(forumService,client);
    }
}
