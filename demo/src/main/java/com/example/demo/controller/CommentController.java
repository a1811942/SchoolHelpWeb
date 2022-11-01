package com.example.demo.controller;

import com.example.demo.common.R.Result;
import com.example.demo.common.util.DetermineTime;
import com.example.demo.entity.Comment;
import com.example.demo.mapper.CommentDao;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/demo/comment")
//@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 评论动态
     * @param comment
     */
    @PostMapping("saveComment")
    public void saveComment(@RequestBody Comment comment){
        comment.setId(UUID.randomUUID().toString());
        comment.setDate(Timestamp.valueOf(LocalDateTime.now()));
        commentService.save(comment);

    }

    /**
     * 根据动态的id查询所有评论
     * @param momentId
     * @return
     */
    @PostMapping("getCommentAndStudentByMomentId")
    public Result<List<Map<String ,Object>>> getCommentAndStudentByMomentId(@RequestBody String  momentId){
        List<Map<String, Object>> list = commentService.getCommentAndStudentByMomentId(momentId);
        return Result.getSuccessResult(list);

    }



}
