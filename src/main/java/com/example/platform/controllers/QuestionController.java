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

    @PostMapping("/createQuestions")
    public ResponseEntity<QuestionModel> createQuestion() {

        QuestionModel questionModel1 = new QuestionModel();
        questionModel1.setTitle("Sorting");
        questionModel1.setDescription("sort the array");
        questionModel1.setParameters("[3, 2, 1], 3");
        questionModel1.setExpectedOutput("[1, 2, 3]");
        questionModel1.setLevel("easy");
        questionRepository.save(questionModel1);

        QuestionModel questionModel2 = new QuestionModel();
        questionModel2.setTitle("Searching");
        questionModel2.setDescription("search for an element in the array");
        questionModel2.setParameters("[3, 2, 1], 3");
        questionModel2.setExpectedOutput("true");
        questionModel2.setLevel("medium");
        questionRepository.save(questionModel2);

        return new ResponseEntity<>(questionModel1, HttpStatus.OK);
    }
}