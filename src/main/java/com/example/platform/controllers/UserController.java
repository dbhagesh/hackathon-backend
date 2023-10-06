package com.example.platform.controllers;

import com.example.platform.models.UsersModel;
import com.example.platform.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UsersRepository usersRepository;

    @PostMapping("/createUser/{name}/{password}")
    public ResponseEntity<Object> createUser( @PathVariable("name") String name, @PathVariable("password") String password ) {
        int count = usersRepository.findAll().size();
        UsersModel user = new UsersModel();
        user.setName(name);
        user.setPassword(password);
        user.setStreak(count);
        user.setRank(count);

        usersRepository.save(user);

        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<Object> getUsers() {
        List<UsersModel> users = usersRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/login/{id}/{name}/{password}")
    public ResponseEntity<Object> login( @PathVariable("id") UUID id, @PathVariable("name") String name, @PathVariable("password") String password ) {
        System.out.println(id);
        UsersModel user = usersRepository.findById(id).get();
        System.out.print(user.getName());
                System.out.println(name);
        if (user.getName().equals(name) && user.getPassword().equals(password)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/folow/{user1}/{user2}")
    public ResponseEntity<Object> follow( @PathVariable("user1") UUID user1, @PathVariable("user2") UUID user2 ) {
        UsersModel userModel1 = usersRepository.getReferenceById(user1);
        UsersModel userModel2 = usersRepository.getReferenceById(user2);

        HashSet<UUID> friends1 = userModel1.getFriends();
        friends1.add(user2);
        usersRepository.save(userModel1);

        HashSet<UUID> friends2 = userModel2.getFriends();
        friends2.add(user1);
        usersRepository.save(userModel2);

        return new ResponseEntity<>( HttpStatus.OK);
    }
}