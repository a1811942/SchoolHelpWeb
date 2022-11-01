package com.example.demo;



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
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@SpringBootTest
public class SendMailTest {
    private RestHighLevelClient client;

    @Resource
    private ForumService forumService;

    @Test
    public void testAddDocument() throws IOException {
        BulkRequest request = new BulkRequest();
        List<ForumDoc> list = forumService.getForumDoc();
        for (ForumDoc doc : list) {
            ForumDoc forumDoc = new ForumDoc(doc);
            request.add(new IndexRequest("forum")
                    .id(forumDoc.getId())
                    .source("json",XContentType.JSON));

        }
        client.bulk(request,RequestOptions.DEFAULT);
    }

    @BeforeEach
    public void serUp(){
        this.client=new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.128.100:9200")
        ));
    }
    @After
    public void tearDown() throws IOException {
        this.client.close();
    }

//    @Test
//    public void sendMessage() {
//        System.out.println("Math.random得到小数");
//        String  a = String.valueOf(Math.round((Math.random()+1) * 1000));
//        System.out.println("a=="+a);
//        System.out.println(Math.round((Math.random()+1) * 1000));
//        System.out.println("Random");
//        System.out.println(new Random().nextInt(9999));
//        System.out.println("字符串前面补0的话就这样String.format");
//        System.out.println(String.format("%04d",new Random().nextInt(9999)));
//
//    }
//    @Test
//    public void detelePhoto(){
//        File file = new File("C:\\Users\\liu\\Desktop\\B4(}O1(J8A(22$`{7N@S6P6.jpg");
//        boolean delete = file.delete();
//        if (delete){
//            System.out.println("删除成功");
//        }
//        System.out.println("失败");
//
//    }

}
