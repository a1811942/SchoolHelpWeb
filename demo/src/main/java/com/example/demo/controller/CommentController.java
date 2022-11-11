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
    public Result<Boolean> saveComment(@RequestBody Comment comment){

        if(comment.getMomentsId()=="" || comment.getMomentsId()==null){
            return Result.getSuccessResult(false);
        }

            comment.setId(UUID.randomUUID().toString());
            comment.setDate(Timestamp.valueOf(LocalDateTime.now()));
            commentService.save(comment);

        return Result.getSuccessResult(true);

    }

    /**
     * 根据动态的id查询所有评论
     * @param momentId
     * @return
     */
    @PostMapping("getCommentAndStudentByMomentId")
    public Result<List<Map<String ,Object>>> getCommentAndStudentByMomentId(@RequestBody  String  momentId){
        List<Map<String, Object>> list = commentService.getCommentAndStudentByMomentId(momentId);
        return Result.getSuccessResult(list);

    }

    /**
     * 根据动态id删除所有评论
     * @param momentId
     * @return
     */
    @PostMapping("deleteByMomentsId")
    public Result<Boolean> deleteByMomentsId(@RequestBody String  momentId){
        Boolean res = commentService.deleteByMomentsId(momentId);
        return Result.getSuccessResult(res);

    }

    /**
     * 根据动态id查询评论数量
     * @param momentId
     * @return
     */
    @PostMapping("getCountById")
    public Result<Integer> getCountById(@RequestBody String  momentId){
        Integer count = commentService.getCountById(momentId);
        return Result.getSuccessResult(count);

    }

    /**
     * 根据commentId 删除评论
     * @param commentId
     * @return
     */
    @DeleteMapping("deleteComment")
    public Result<Integer> deleteCommentById( String  commentId){
        Integer res = commentService.deleteByCommentIs(commentId);
        return Result.getSuccessResult(res);

    }
}
