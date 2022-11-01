package com.example.demo.common.intercept;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.R.Result;
import com.example.demo.common.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
//        Cookie[] cookies = request.getCookies();
//        if (cookies!=null) {
//            for (Cookie cookie : cookies) {
//                System.out.println("cookies删除之前======" + cookie.getName() + ":" + cookie.getValue());
//            }
//        }
//
//
//        if (cookies!=null) {
//            for (Cookie cookie : cookies) {
//               if (jwtTokenUtil.isJwtExpired(cookie.getValue())){
//                   cookie.setMaxAge(0);
//                   cookie.setPath("/");
//                   response.addCookie(cookie);
//
//               }
//                System.out.println("删除之后11"+ cookie.getName() + ":" + cookie.getValue());
//            }
//        }
//        if (cookies!=null) {
//            for (Cookie cookie : cookies) {
//                System.out.println("cookies删除之后22======" + cookie.getName() + ":" + cookie.getValue());
//            }
//        }
        String token = request.getHeader("token");
        token = token == null ? "" : token;
        Boolean res = jwtTokenUtil.isJwtExpired(token);
        System.out.println("token=="+token);

        if (res){
            return true;
        }
        String string = JSONObject.toJSONString(Result.getMessageResult(null, "401"));
        PrintWriter out = response.getWriter();
        out.write(string);
        return false;
    }
}
