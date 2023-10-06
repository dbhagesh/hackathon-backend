package com.example.platform.controllers;

import com.example.platform.models.QuestionModel;
import com.example.platform.models.RoomModel;
import com.example.platform.models.UsersModel;
import com.example.platform.repository.QuestionRepository;
import com.example.platform.repository.RoomRepository;
import com.example.platform.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
    @Autowired
    private final UsersRepository usersRepository;
    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final QuestionRepository questionRepository;

    @PostMapping("/createRoom/{name}/{timer}/{level}/")
    public ResponseEntity<Object> createRoom(@PathVariable("name") String name,@PathVariable("timer") Date timer, @PathVariable("level") String level) {
        List<QuestionModel> questions = questionRepository.findAll();
        RoomModel roomModel = new RoomModel();
        roomModel.setName(name);
        roomModel.setLevel(level);
        roomModel.setTimer(timer);
        // pick only random 4
        ArrayList<UUID> addedQuestions = new ArrayList<>();

        Random random = new Random();
        int randomQuestions = 4;
        for (int i = 0; i < randomQuestions; i++) {
            int indx = random.nextInt(questions.size());
            UUID qstn = questions.get(indx).getId();
            addedQuestions.add(qstn);
        }

        roomModel.setQuestions(addedQuestions);

        roomRepository.save(roomModel);
        return new ResponseEntity<>(roomModel, HttpStatus.OK);
    }

    // To join a room
    @PostMapping("/addUser/{roomId}/{userId}")
    public ResponseEntity<Object> addUser(@PathVariable("roomId") UUID roomId, @PathVariable("userId") UUID userId) {
        RoomModel roomModel = roomRepository.findById(roomId).get();
        ArrayList<UUID> users = roomModel.getUsers();
        users.add(userId);
        roomModel.setUsers(users);
        roomRepository.save(roomModel);
        return new ResponseEntity<>(roomModel, HttpStatus.OK);
    }

    @PostMapping("/removeUser/{roomId}/{userId}")
    public ResponseEntity<Object> removeUser(@PathVariable("roomId") UUID roomId, @PathVariable("userId") UUID userId) {
        RoomModel roomModel = roomRepository.findById(roomId).get();
        ArrayList<UUID> users = roomModel.getUsers();
        users.remove(userId);
        roomModel.setUsers(users);
        roomRepository.save(roomModel);
        return new ResponseEntity<>(roomModel, HttpStatus.OK);
    }
}