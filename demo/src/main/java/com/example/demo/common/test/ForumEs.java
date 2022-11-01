package com.example.demo.common.test;

import com.example.demo.entity.ForumDoc;
import com.example.demo.service.ForumService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

public class ForumEs {

    @Autowired
    private ForumService forumService;

    public static void main(String[] args) throws IOException {
        ForumEs forumEs = new ForumEs();
        forumEs.testAddDocument();
    }
        public  void testAddDocument() throws IOException {

        }







}
