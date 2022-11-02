package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Forum;
import com.example.demo.entity.ForumDoc;
import com.example.demo.mapper.ForumDao;
import com.example.demo.service.ForumService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ForumServiceImpl extends ServiceImpl<ForumDao, Forum> implements ForumService {
    @Autowired
    private ForumDao forumDao;

    @Autowired
    private RestHighLevelClient client;

    /**
     * 查询所有的文章和学生信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> getALlForumAndStudent(Integer pageNum,Integer pageSize) {
        List<Map<String, Object>> list = forumDao.getALlForumAndStudent();
        PageHelper.startPage(pageNum,pageSize);
        for (Map<String, Object> map : list) {
            Timestamp date = (Timestamp) map.get("date");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String format = simpleDateFormat.format(date);
            map.put("date",format);
        }
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 保存文章
     * @param forum
     * @return
     */
    @Override
    public Boolean saveForum(Forum forum) throws IOException {
        String forumId= UUID.randomUUID().toString();
        forum.setId(forumId);
        forum.setDate(Timestamp.valueOf(LocalDateTime.now()));
        boolean res=false;
            res = this.save(forum);
            ForumDoc forumDoc = this.getForumDocById(forumId);
            IndexRequest request = new IndexRequest("forum").id(forumId);
            request.source(JSON.toJSONString(forumDoc), XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);

        return res;
    }

    @Override
    public Boolean deleteForum(String forumId) {
        boolean res=false;
        try {
            DeleteRequest request = new DeleteRequest("forum").id(forumId);
            client.delete(request,RequestOptions.DEFAULT);
            res = this.removeById(forumId);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 修改文章
     * @param forum
     * @return
     */
    @Override
    public Boolean updateForum(Forum forum) {
        forum.setDate(Timestamp.valueOf(LocalDateTime.now()));
        boolean res=false;
        try {
            res = this.updateById(forum);
            UpdateRequest request = new UpdateRequest("forum", forum.getId());
            request.doc("title",forum.getTitle(),
                    "content",forum.getForumContent());
            client.update(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 查询forum 和 studen 封装到ForumDoc
     * @return
     */
    @Override
    public List<ForumDoc> getForumDoc() {
        List<ForumDoc> list = forumDao.getForumDoc();
        return list;
    }

    /**
     * 根据forumid查 forum 和 studen 封装到ForumDoc
     * @param forumId
     * @return
     */
    @Override
    public ForumDoc getForumDocById(String forumId) {
        ForumDoc forumDoc = forumDao.getForumDocById(forumId);
        return forumDoc;
    }
}
