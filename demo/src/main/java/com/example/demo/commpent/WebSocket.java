package com.example.demo.commpent;

import com.example.demo.common.R.MessageUtils;
import com.example.demo.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/demo/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class WebSocket {

    //存储每一个客户端对象对应的 WebSocket对象
    private static Map<String ,WebSocket> onlineUsers= new ConcurrentHashMap<>();

    //可以发送消息给指定的用户
    private Session session;


    private HttpSession httpSession;

    //链接建立时调用
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session=session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession=httpSession;
        String  studentName = (String) httpSession.getAttribute("studentName");
        String  studentId = (String) httpSession.getAttribute("studentId");
        System.out.println("studentName======"+studentName);
        System.out.println("studentId======"+studentId);
        //将当前对象存储起来
        onlineUsers.put(studentName,this);

        //将当前在线用户名推送给所有客户端
     }

    //接收到客户端发送的数据时调用
    @OnMessage
    public void onMessage(String message,Session session)  {
        ObjectMapper mapper=new ObjectMapper();
        try {
            //将字符串转化为对象
            Message mes = mapper.readValue(message, Message.class);
            //要发给谁
            String toStudentName = mes.getToStudentName();
            String data = mes.getMessage();
            //谁发送的
            String  studentName = (String) httpSession.getAttribute("studentName");
            String resultMessage = MessageUtils.getMessage(studentName, data);
            System.out.println("resultMessage======"+resultMessage);

            onlineUsers.get(toStudentName).session.getBasicRemote().sendText(resultMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //链接关闭时调用
    @OnClose
    public void onClose(Session session){

    }
}
