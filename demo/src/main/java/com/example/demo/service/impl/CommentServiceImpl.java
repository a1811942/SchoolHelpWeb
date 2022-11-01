package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.util.DetermineTime;
import com.example.demo.entity.Comment;
import com.example.demo.mapper.CommentDao;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    private CommentDao commentDao;


    /**
     * 根据动态的id查询所有评论
     * @param momentId
     * @return
     */
    @Override
    public List<Map<String, Object>> getCommentAndStudentByMomentId(String momentId) {
        List<Map<String, Object>> list = commentDao.getCommentAndStudentByMomentId(momentId);
        if (list.size()>1){
            for (Map<String, Object> map : list) {
                Timestamp date = (Timestamp) map.get("date");
                String s = DetermineTime.showDate(date, "yyyy年MM月dd日HH:mm");
                map.put("date",s);
            }
        }

        return list;
    }
}
