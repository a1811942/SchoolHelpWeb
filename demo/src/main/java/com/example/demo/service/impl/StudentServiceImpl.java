package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.R.Result;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentDao;
import com.example.demo.service.StudentService;
import com.example.demo.service.UpdateAndDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private UpdateAndDownService updateAndDownService;
    @Autowired
    private RedisTemplate<String ,Object> redisTemplate;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public Map<String, String> login(String username, String password) {
        Map<String ,String > map=new HashMap<>();
        //查询用户名为xxx的数量(用户名不重复)
        int res = Math.toIntExact(query().eq("username", username).count());
        if (res==0){
            //用户名不存在
            map.put("res","-1");
            return map;
        }
        Student login = query().eq("username", username).one();
        if (password.equals(login.getPassword())){//判断密码是否正确
            //登录成功
            System.out.println("studentName====="+login.getName());
            map.put("studentId",login.getId());
            map.put("studentName",login.getName());
            map.put("res","1");
            map.put("username",login.getUsername());
            return map;
        }else {
            //登录不成功
            map.put("res","-2");
            return map;
        }
    }

    /**
     * 注册
     * @param student
     * @param code
     * @return
     */
    @Override
    public Result<Map<String ,String >> register(Student student, String code) {
        Long expire =0L;
        if (code!=null){ //判断验证码是否正确
            expire = redisTemplate.getExpire(code);
        }
        if (expire<=0){
            //验证码错误
            return Result.getMessageResult(null, "A002");
        }

        String email = student.getEmail();
        List<Student> list = studentDao.getEmails();
        String finalEmail = email;
        List<Student> collect = list.stream().filter(e -> finalEmail.equals(e.getEmail())).collect(Collectors.toList());
        if (collect.size()==0){
            //邮箱未被绑定
            student.setId(UUID.randomUUID().toString());
            this.save(student);
            return Result.getMessageResult(null, "A001");
        }
        if (collect.size()>0){
            //邮箱以被绑定
            return Result.getMessageResult(null, "A002");
        }
        //邮箱错误
        return Result.getMessageResult(null, "A003");

    }

    /**
     * 修改个人信息
     * @param student
     * @return
     */
    @Override
    public Boolean update(Student student) {
        int i = studentDao.updateById(student);
        if (i>0){
            return true;

        }
        return false;
    }

    /**
     * 根据id查询信息
     * @param studentId
     * @return
     */
    @Override
    public Student getStudentById(String studentId) {
        Student student = this.getById(studentId);
        return student;
    }

    /**
     * 查询个人信息
     * @param username
     * @return
     */
    @Override
    public Student select(String username) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getUsername,username);
        Student student = this.getOne(queryWrapper);
        return student;
    }

    /**
     * 修改头像
     * @param student
     * @return
     */
    @Override
    public Boolean updateAvatarByStudentId(String avatar,String studentId,String oldAvatar) {
        Boolean res = studentDao.updateAvatarByStudentId(avatar, studentId);
        updateAndDownService.deletePhoto(oldAvatar);
        return res;
    }
}
