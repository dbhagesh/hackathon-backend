package com.example.platform.controllers;

import com.example.platform.models.QuestionModel;
import com.example.platform.models.UsersModel;
import com.example.platform.repository.QuestionRepository;
import com.example.platform.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {
    @Autowired
    private final UsersRepository usersRepository;
    @Autowired
    private final QuestionRepository questionRepository;

    @GetMapping("/questions")
    public ResponseEntity<Object> getQuestions() {
        List<QuestionModel> questions = questionRepository.findAll();

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}