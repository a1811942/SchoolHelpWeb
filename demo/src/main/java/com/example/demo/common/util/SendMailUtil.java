package com.example.demo.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SendMailUtil {
    @Autowired
    private JavaMailSender javaMailSender;


    public String sendMessage(String email) {
        String  text = String.valueOf(Math.round((Math.random()+1) * 1000));
//        String text= UUID.randomUUID().toString();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("验证码");
        simpleMailMessage.setText("验证码："+text);
        simpleMailMessage.setFrom("2940193593@qq.com");
        javaMailSender.send(simpleMailMessage);
        return text;

    }
}
