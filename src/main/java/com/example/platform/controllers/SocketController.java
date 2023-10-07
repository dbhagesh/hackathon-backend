package com.example.platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;

@RestController
public class SocketController {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @GetMapping("/socket")
    public void socketSub(){
       simpMessagingTemplate.convertAndSend("/topic/transaction","fuckOff");
    }





}
