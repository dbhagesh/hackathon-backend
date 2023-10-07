package com.example.platform.controllers;

import com.example.platform.models.QuestionModel;
import com.example.platform.models.RoomDTO;
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

import static java.lang.Math.min;

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

    @GetMapping("/getRoom/{id}")
    public ResponseEntity<Object> getRoom(@PathVariable("id") UUID id) {
        Optional<RoomModel> model = roomRepository.findById(id);

        if(model.isPresent()) {
            RoomModel rm = model.get();

            ArrayList<UsersModel> users = new ArrayList<>();
            for(UUID uId : rm.getUsers()) {
                Optional<UsersModel> usr = usersRepository.findById(uId);
                usr.ifPresent(users::add);
            }

            ArrayList<QuestionModel> questions = new ArrayList<>();
            for(UUID qId : rm.getQuestions()) {
                Optional<QuestionModel> q = questionRepository.findById(qId);
                q.ifPresent(questions::add);
            }
            return ResponseEntity.ok().body(RoomDTO.builder().id(rm.getId()).name(rm.getName()).users(users).questions(questions).timer(rm.getTimer()).level(rm.getLevel()).build());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not found!");
        }
    }

    @PostMapping("/createRoom/{name}/{timer}/{level}")
    public ResponseEntity<Object> createRoom(@PathVariable("name") String name,@PathVariable("timer") int timer, @PathVariable("level") String level) {
        List<QuestionModel> questions = questionRepository.findAll();
        RoomModel roomModel = new RoomModel();
        roomModel.setName(name);
        roomModel.setLevel(level);
        // Create a Date instance representing the current date and time
        Date date = new Date();

        // Convert the Date to a Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Add 60 minutes
        calendar.add(Calendar.MINUTE, 60);

        // Convert the Calendar back to a Date
        Date updatedDate = calendar.getTime();
        roomModel.setTimer(updatedDate);
        // pick only random 4
        ArrayList<UUID> addedQuestions = new ArrayList<>();

        Random random = new Random();
        int randomQuestions = 4;
        for (int i = 0; i < min(randomQuestions, questions.size()); i++) {
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
        RoomModel roomModel = roomRepository.getReferenceById(roomId);
        List<UUID> users = roomModel.getUsers();
        users.add(userId);
        roomModel.setUsers(users);
        roomRepository.save(roomModel);
        return new ResponseEntity<>( HttpStatus.OK);
    }


    @PostMapping("/removeUser/{roomId}/{userId}")
    public ResponseEntity<Object> removeUser(@PathVariable("roomId") UUID roomId, @PathVariable("userId") UUID userId) {
        RoomModel roomModel = roomRepository.findById(roomId).get();
        List<UUID> users = roomModel.getUsers();
        users.remove(userId);
        roomModel.setUsers(users);
        roomRepository.save(roomModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getRooms")
    public ResponseEntity<Object> getRooms() {
        List<RoomModel> rooms = roomRepository.findAll();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

}