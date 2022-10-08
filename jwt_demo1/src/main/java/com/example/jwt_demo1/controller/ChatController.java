package com.example.jwt_demo1.controller;


import com.example.jwt_demo1.Messager.Message;
import com.example.jwt_demo1.payload.OutputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/")
public class ChatController {
    @GetMapping("/chatindex")
    public String getHomepage() {
        return "index.html";
    }
    @MessageMapping("/chat")
    @SendTo("topic/messager")
    public OutputMessage send(Message message) throws Exception{
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(),message.getText(),time);
    }
}

