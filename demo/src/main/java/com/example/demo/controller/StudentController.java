package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.common.R.Result;
import com.example.demo.common.util.JwtTokenUtil;
import com.example.demo.common.util.SendMailUtil;
import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentDao;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 登录
 */
//@CrossOrigin
@RestController
@RequestMapping("/demo/student")
public class StudentController {

    @Autowired
    private RedisTemplate<String ,Object> redisTemplate;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SendMailUtil sendMailUtil;

    /**
     * 登录
     * @param student
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result<Map<String ,String >> login(@RequestBody Student student, HttpServletRequest request){
        Map<String, String> map = studentService.login(student.getUsername(), student.getPassword());
        if ("1".equals(map.get("res"))){//登录成功
            //生成token
            String token = JwtTokenUtil.createToken(map.get("username"));
            //存到redis中 30分钟
            redisTemplate.opsForValue().set(token,map.get("username"), Duration.ofMinutes(30L));
            map.put("token",token);
            HttpSession session = request.getSession();
            System.out.println("session名称1===="+session);
            session.setAttribute("studentId",map.get("studentId"));
            session.setAttribute("studentName",map.get("studentName"));
            System.out.println("session=="+ session.getAttribute("studentId"));
            System.out.println("studentNameSession=="+ session.getAttribute("studentName"));
            return Result.getSuccessResult(map);
        } else if("-1".equals(map.get("map"))) {
            return Result.getMessageResult(null, "A001");
        } else {
            return Result.getMessageResult(null, "A002");
        }

    }

    @PostMapping("/loginOut")
    public Result<Map<String ,String >> loginOut( HttpServletRequest request){
        String token = request.getHeader("token");
        Boolean res = redisTemplate.delete(token);
        if (res=false){
            return Result.getMessageResult(null,"A002");
        }

        return Result.getMessageResult(null,"A001");
    }

    /**
     * 发送验证码
     * @param student
     * @return
     */
    @PostMapping("/email")
    public Result<Map<String ,String >> email(@RequestBody Student student){
        String email = student.getEmail();
        System.out.println(email);
        LambdaQueryWrapper<Student> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getEmail,email);
        int count = studentService.count(queryWrapper);
        if (count==0){
            String text = sendMailUtil.sendMessage(email);
            redisTemplate.opsForValue().set(text,"text",Duration.ofMinutes(1L));//缓存redis1分钟
            return Result.getMessageResult(null, "A001");
        }
        return Result.getMessageResult(null, "A002");



    }

    /**
     * 注册
     * @param studentDto
     * @return
     */
    @PostMapping("/register")
    public Result<Map<String ,String >> register(@RequestBody StudentDto studentDto){
        Student student = studentDto.getStudent();
        String code = studentDto.getCode();

        Result<Map<String, String>> register = studentService.register(student, code);
        String msgId = register.getMsgId();
        return Result.getMessageResult(null, msgId);

    }

    /**
     * 修改个人信息
     * @param student
     * @return
     */
    @PostMapping("/update")
    public Result<Map<String ,String >> update(@RequestBody Student student){
        System.out.println("student=="+student);
        Boolean res = studentService.update(student);
        if (res){
            return Result.getSuccessResult(null);
        }

        return Result.getMessageResult(null, "A002");
    }


    /**
     * 查询个人信息
     * @param username
     * @return
     */
    @PostMapping("/select")
    public Result<Student> select(@RequestBody String username){

        Student student = studentService.select(username);
        return Result.getSuccessResult(student);

    }
    @PostMapping("/getStudentById")
    public Result<Student> getStudentById(@RequestBody String studentId){

        Student student = studentService.getStudentById(studentId);
        return Result.getSuccessResult(student);

    }

}
