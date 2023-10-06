package com.example.platform.controllers;

import com.example.platform.models.MessageModel;
import com.example.platform.models.RoomModel;
import com.example.platform.models.UsersModel;
import com.example.platform.repository.MessageRepository;
import com.example.platform.repository.RoomRepository;
import com.example.platform.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private final MessageRepository messageRepository;

    @Autowired
    private final RoomRepository roomRepository;

    @PostMapping("/sendMessage/{roomId}/{userId}/{message}")
    public ResponseEntity<Object> sendMessage(@PathVariable("userId") UUID userId, @PathVariable("roomId") UUID roomId, @PathVariable("message") String message ) {
        MessageModel messageModel = new MessageModel();
        messageModel.setMessage(message);
        messageModel.setMessageTime(new Date());

        messageRepository.save(messageModel);

        RoomModel roomModel = roomRepository.getReferenceById(roomId);
        List<MessageModel> messages = roomModel.getMessages();
        messages.add(messageModel);
        roomModel.setMessages(messages);

        roomRepository.save(roomModel);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/login/{id}/{name}/{password}")
    public ResponseEntity<Object> login(@PathVariable("id") UUID id, @PathVariable("name") String name, @PathVariable("password") String password ) {
        UsersModel user = usersRepository.getReferenceById(id);
        if (user.getName().equals(name) && user.getPassword().equals(password)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }
}