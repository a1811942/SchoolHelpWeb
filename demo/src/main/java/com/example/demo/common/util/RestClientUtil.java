package com.example.demo.common.util;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.ForumDoc;
import com.example.demo.service.ForumService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
@Component
public class RestClientUtil {

    public static void saveAll(ForumService forumService, RestHighLevelClient client) throws IOException {
        BulkRequest request = new BulkRequest();
        List<ForumDoc> list3 = forumService.getForumDoc();
        for (ForumDoc doc : list3) {
        ForumDoc forumDoc = new ForumDoc(doc);
        request.add(new IndexRequest("forum")
                .id(forumDoc.getId())
                .source(JSON.toJSONString(forumDoc), XContentType.JSON));

    }
        client.bulk(request, RequestOptions.DEFAULT);
    }
}
