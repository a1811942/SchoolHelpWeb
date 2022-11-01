package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Forum;
import com.example.demo.entity.ForumDoc;
import com.example.demo.mapper.ForumDao;
import com.example.demo.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class ForumServiceImpl extends ServiceImpl<ForumDao, Forum> implements ForumService {
    @Autowired
    private ForumDao forumDao;

    @Override
    public List<Map<String, Object>> getALlForumAndStudent() {
        List<Map<String, Object>> list = forumDao.getALlForumAndStudent();
        for (Map<String, Object> map : list) {
            Timestamp date = (Timestamp) map.get("date");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String format = simpleDateFormat.format(date);
            map.put("date",format);
        }
        return list;
    }

    @Override
    public List<ForumDoc> getForumDoc() {
        List<ForumDoc> list = forumDao.getForumDoc();
        return list;
    }
}
