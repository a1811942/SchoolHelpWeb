package com.example.demo.controller;

import com.example.demo.common.R.Result;
import com.example.demo.common.util.RestClientUtil;
import com.example.demo.entity.ForumDoc;
import com.example.demo.service.ForumService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demo/forum")
public class ForumController {
    @Autowired
    private ForumService forumService;
    @Autowired
    private RestHighLevelClient client;

    @PostMapping("/getALlForumAndStudent")
    public Result<List<Map<String, Object>>> getALlForumAndStudent() throws IOException {
        List<ForumDoc> list2 = forumService.getForumDoc();
        RestClientUtil.saveAll(forumService,client);
        List<Map<String, Object>> list = forumService.getALlForumAndStudent();
        return Result.getSuccessResult(list);
    }
}
